package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.evaluation.EvaluationResult;
import com.fastlearner.project0.dto.judge0.JudgeResult;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.ProblemTemplate;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.ProblemTemplateRepository;
import com.fastlearner.project0.repository.TestCaseRepository;
import com.fastlearner.project0.service.JudgeService;
import com.fastlearner.project0.service.SubmissionEvaluatorService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubmissionEvaluatorServiceImpl implements SubmissionEvaluatorService
{
    private final TestCaseRepository testCaseRepository;
    private final JudgeService judgeService;
    private final ProblemTemplateRepository problemTemplateRepository;
    public SubmissionEvaluatorServiceImpl(TestCaseRepository testCaseRepository, JudgeService judgeService, ProblemTemplateRepository problemTemplateRepository) {
        this.testCaseRepository = testCaseRepository;
        this.judgeService = judgeService;
        this.problemTemplateRepository = problemTemplateRepository;
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
            if(expectedOutputArray[i].equals(actualOutputArray[i])) passedTestCases++;
            else break;
        }
        return passedTestCases;
    }
    @Override
    public EvaluationResult evaluate(Problem problem, Language language, String sourceCode) {
        List<TestCase> testCases = testCaseRepository.findByProblemId(problem.getId()).orElseThrow(() -> new ResourceNotFoundException("TEST_CASE_NOT_FOUND"));

        ProblemTemplate problemTemplate = problemTemplateRepository.findByProblemAndLanguage(problem,language).orElseThrow(()->new ResourceNotFoundException("DRIVER_CODE_NOT_FOUND_SubmissionServiceImpl"));
        String driverCode = problemTemplate.getDriverCode();
        String finalCode = driverCode+"\n"+sourceCode;
        StringBuilder input = new StringBuilder();
        StringBuilder expectedOutput = new StringBuilder();
        input.append(testCases.size());
        input.append("\n");
        for(TestCase testCase : testCases)
        {
            input.append(testCase.getInputData());
            expectedOutput.append(testCase.getExpectedOutput());
            input.append("\n");
            expectedOutput.append("\n");
        }
        JudgeResult result =
                judgeService.execute(
                        finalCode,
                        language,
                        input.toString()
                );

        int passedTestCases=0;
        String actualOutput = result.getOutput();
        EvaluationResult evaluationResult = new EvaluationResult();
        evaluationResult.setVerdict(result.getVerdict());
        passedTestCases = getPassedTestCases(expectedOutput.toString(),actualOutput);
        if(result.getVerdict().equals(Verdict.ACCEPTED))
        {
            Verdict verdict = passedTestCases==testCases.size() ?  Verdict.ACCEPTED : Verdict.WRONG_ANSWER;
            evaluationResult.setVerdict(verdict);
        }
        evaluationResult.setPassedTestCases(passedTestCases);
        evaluationResult.setExecutionTimeMs(result.getExecutionTimeMs()==null?0:result.getExecutionTimeMs());
        evaluationResult.setMemoryUsedKb(result.getMemoryUsedKb()==0?0:result.getMemoryUsedKb());
        evaluationResult.setTotalTestCases(testCases.size());
        evaluationResult.setErrorMessage(result.getErrorMessage());
        return evaluationResult;
    }
}
