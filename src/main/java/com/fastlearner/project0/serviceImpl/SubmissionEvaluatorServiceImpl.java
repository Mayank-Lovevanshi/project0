package com.fastlearner.project0.serviceImpl;


import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.SubmissionStatus;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.SubmissionRepository;
import com.fastlearner.project0.repository.TestCaseRepository;
import com.fastlearner.project0.service.SubmissionEvaluatorService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
@Service
public class SubmissionEvaluatorServiceImpl implements SubmissionEvaluatorService
{
    private final TestCaseRepository testCaseRepository;
    private final SubmissionRepository submissionRepository;

    public SubmissionEvaluatorServiceImpl(TestCaseRepository testCaseRepository, SubmissionRepository submissionRepository1) {
        this.testCaseRepository = testCaseRepository;
        this.submissionRepository = submissionRepository1;
    }

    private String normalize(String s)
    {
        return s
                .replace("\r\n", "\n")
                .replace("\r", "\n")
                .trim();
    }

    private int getPassedTestCases(String expectedOutput,String actualOutput)
    {
        if(actualOutput==null) return 0;
        String[] expectedOutputArray = expectedOutput.split("\n");
        String[] actualOutputArray = actualOutput.split("\n");
        int n = Math.min(expectedOutputArray.length,actualOutputArray.length);
        int passedTestCases = 0;
        //if(expectedOutputArray.length != actualOutputArray.length) throw new InvalidDriverCodeException("SOME_ISSUE_IN_DRIVER_CODE");
        for(int i=0;i<n;i++)
        {
            if(normalize(expectedOutputArray[i]).equals(normalize(actualOutputArray[i]))) passedTestCases++;
            else break;
        }
        return passedTestCases;
    }
    /*
    public class Judge0SubmissionResponse
{
    private String stdout;
    private String stderr;
    private String compile_output;
    private String time;
    private Integer memory;
    private Judge0Status status;
    private String token;
    private String message;
}
     */
    private Verdict mapVerdict(Judge0SubmissionResponse response)
    {
        Integer statusId = response.getStatus().getId();

        return switch (statusId)
        {
            case 3 -> Verdict.ACCEPTED;
            case 5 -> Verdict.TIME_LIMIT_EXCEEDED;
            case 6 -> Verdict.COMPILATION_ERROR;
            case 7,8,9,10,11,12 -> Verdict.RUNTIME_ERROR;
            default -> Verdict.SYSTEM_ERROR;
        };
    }
    private void modifySubmission(Judge0SubmissionResponse result, String expectedOutput, int size, Submission submission)
    {
        int passedTestCases=0;
        String actualOutput = new String(Base64.getDecoder().decode(result.getStdout().trim()));
        Verdict judgeVerdict = mapVerdict(result);
        passedTestCases = getPassedTestCases(expectedOutput,actualOutput);
        if(judgeVerdict.equals(Verdict.ACCEPTED))
        {
            Verdict verdict = passedTestCases==size ?  Verdict.ACCEPTED : Verdict.WRONG_ANSWER;
            submission.setVerdict(verdict);
        }
        submission.setPassedTestCases(passedTestCases);
        submission.setExecutionTimeMs(result.getTime()==null?"0":result.getTime());
        submission.setMemoryUsedKb(result.getMemory()==0?0:result.getMemory());
        submission.setTotalTestCases(size);
        submission.setErrorMessage(result.getMessage());
        submission.setStatus(SubmissionStatus.COMPLETED);
    }
    private String buildTestCasesData(List<TestCase> testCases)
    {
        StringBuilder expectedOutput = new StringBuilder();
        for(TestCase testCase : testCases)
        {
            expectedOutput.append(testCase.getExpectedOutput());
            expectedOutput.append("\n");
        }
        return expectedOutput.toString();
    }

    @Override
    public void evaluateResponse(Judge0SubmissionResponse response) {
        Submission submission = submissionRepository.findByToken(response.getToken()).orElseThrow(() -> new ResourceNotFoundException("SUBMISSION_NOT_FOUND"));
        Problem problem = submission.getProblem();
        List<TestCase> testCases = testCaseRepository.findByProblemId(problem.getId()).orElseThrow(() -> new ResourceNotFoundException("TEST_CASE_NOT_FOUND"));
        String expectedOutput = buildTestCasesData(testCases);
        modifySubmission(response,expectedOutput,testCases.size(),submission);
        submissionRepository.save(submission);
    }

}
