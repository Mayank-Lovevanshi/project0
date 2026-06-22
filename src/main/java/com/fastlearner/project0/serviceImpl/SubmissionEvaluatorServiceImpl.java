package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.evaluation.EvaluationResult;
import com.fastlearner.project0.dto.judge.JudgeDTO;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionRequest;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.dto.judge0.JudgeResult;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.ProblemTemplate;
import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.ProblemTemplateRepository;
import com.fastlearner.project0.repository.SubmissionRepository;
import com.fastlearner.project0.repository.TestCaseRepository;
import com.fastlearner.project0.service.JudgeService;
import com.fastlearner.project0.service.SubmissionEvaluatorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SubmissionEvaluatorServiceImpl implements SubmissionEvaluatorService
{
    private final TestCaseRepository testCaseRepository;
    private final JudgeService judgeService;
    private final ProblemTemplateRepository problemTemplateRepository;
    private final SubmissionRepository submissionRepository;
    public SubmissionEvaluatorServiceImpl(TestCaseRepository testCaseRepository, JudgeService judgeService, ProblemTemplateRepository problemTemplateRepository, SubmissionRepository submissionRepository) {
        this.testCaseRepository = testCaseRepository;
        this.judgeService = judgeService;
        this.problemTemplateRepository = problemTemplateRepository;
        this.submissionRepository = submissionRepository;
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
    private EvaluationResult applyResults(JudgeResult result,StringBuilder expectedOutput,int size)
    {
        int passedTestCases=0;
        String actualOutput = result.getOutput();
        EvaluationResult evaluationResult = new EvaluationResult();
        evaluationResult.setVerdict(result.getVerdict());
        passedTestCases = getPassedTestCases(expectedOutput.toString(),actualOutput);
        if(result.getVerdict().equals(Verdict.ACCEPTED))
        {
            Verdict verdict = passedTestCases==size ?  Verdict.ACCEPTED : Verdict.WRONG_ANSWER;
            evaluationResult.setVerdict(verdict);
        }
        evaluationResult.setPassedTestCases(passedTestCases);
        evaluationResult.setExecutionTimeMs(result.getExecutionTimeMs()==null?0:result.getExecutionTimeMs());
        evaluationResult.setMemoryUsedKb(result.getMemoryUsedKb()==0?0:result.getMemoryUsedKb());
        evaluationResult.setTotalTestCases(size);
        evaluationResult.setErrorMessage(result.getErrorMessage());
        return evaluationResult;
    }
    private void applyTestCasesData(List<TestCase> testCases,StringBuilder expectedOutput,StringBuilder input)
    {
        input.append(testCases.size());
        input.append("\n");
        for(TestCase testCase : testCases)
        {
            input.append(testCase.getInputData());
            expectedOutput.append(testCase.getExpectedOutput());
            input.append("\n");
            expectedOutput.append("\n");
        }
    }

    @Override
    public EvaluationResult evaluate(Problem problem, Language language, String sourceCode) {
        List<TestCase> testCases = testCaseRepository.findByProblemId(problem.getId()).orElseThrow(() -> new ResourceNotFoundException("TEST_CASE_NOT_FOUND"));

        ProblemTemplate problemTemplate = problemTemplateRepository.findByProblemAndLanguage(problem,language).orElseThrow(()->new ResourceNotFoundException("DRIVER_CODE_NOT_FOUND_SubmissionServiceImpl"));
        String driverCode = problemTemplate.getDriverCode();
        String finalCode = driverCode+"\n"+sourceCode;
        StringBuilder input = new StringBuilder();
        StringBuilder expectedOutput = new StringBuilder();
        applyTestCasesData(testCases,input,expectedOutput);
        String token =
                judgeService.execute(
                        finalCode,
                        language,
                        input.toString()
                );
        JudgeResult result = judgeService.getResult(token);

        return applyResults(result,expectedOutput,testCases.size());
    }
    /*
    //@Override
    public void evaluateBatch(List<Submission> submissions) {
        List<JudgeDTO> submissionsToJudge = new ArrayList<>();
        for(Submission submission : submissions)
        {
            Problem problem = submission.getProblem();
            Language language = submission.getLanguage();
            String sourceCode = submission.getSourceCode();
            ProblemTemplate problemTemplate = problemTemplateRepository.findByProblemAndLanguage(problem,language).orElseThrow(()->new ResourceNotFoundException("DRIVER_CODE_NOT_FOUND_SubmissionServiceImpl"));
            String driverCode = problemTemplate.getDriverCode();
            String finalCode = driverCode+"\n"+sourceCode;
            List<TestCase> testCases = testCaseRepository.findByProblemId(problem.getId()).orElseThrow(() -> new ResourceNotFoundException("TEST_CASE_NOT_FOUND"));
            StringBuilder expectedOutput = new StringBuilder();
            StringBuilder input = new StringBuilder();
            applyTestCasesData(testCases,input,expectedOutput);
            submissionsToJudge.add(new JudgeDTO(finalCode,language,input.toString()));
        }
        String[] tokens = judgeService.executeBatch(submissionsToJudge);
        for(int i=0;i<tokens.length;i++)
        {
            submissions.get(i).setJudge0Token(tokens[i]);
            submissionRepository.save(submissions.get(i));
        }
    }
     */
}
