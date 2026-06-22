package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.evaluation.EvaluationResult;
import com.fastlearner.project0.dto.judge.JudgeDTO;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.ProblemTemplate;
import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.enums.SubmissionStatus;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.ProblemTemplateRepository;
import com.fastlearner.project0.repository.SubmissionRepository;
import com.fastlearner.project0.repository.TestCaseRepository;
import com.fastlearner.project0.service.JudgeService;
import com.fastlearner.project0.service.SubmissionEvaluatorService;
import com.fastlearner.project0.service.SubmissionProcessingService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmissionProcessingServiceImpl implements SubmissionProcessingService {
    private final SubmissionEvaluatorService submissionEvaluatorService;
    private final SubmissionRepository submissionRepository;
    private final ProblemTemplateRepository problemTemplateRepository;
    private final TestCaseRepository testCaseRepository;
    private final JudgeService judgeService;

    public SubmissionProcessingServiceImpl(SubmissionEvaluatorService submissionEvaluatorService, SubmissionRepository submissionRepository, ModelMapper modelMapper, ProblemTemplateRepository problemTemplateRepository, TestCaseRepository testCaseRepository, JudgeService judgeService) {
        this.submissionEvaluatorService = submissionEvaluatorService;
        this.submissionRepository = submissionRepository;
        this.problemTemplateRepository = problemTemplateRepository;
        this.testCaseRepository = testCaseRepository;
        this.judgeService = judgeService;
    }
    private void applyResult(EvaluationResult result, Submission submission)
    {
        submission.setVerdict(result.getVerdict());
        submission.setPassedTestCases(result.getPassedTestCases());
        submission.setExecutionTimeMs(result.getExecutionTimeMs());
        submission.setMemoryUsedKb(result.getMemoryUsedKb());
        submission.setTotalTestCases(result.getTotalTestCases());
        submission.setErrorMessage(result.getErrorMessage());
    }
    @Override
    @Async
    public void processSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(()->new ResourceNotFoundException("SUBMISSION_NOT_FOUND"));
        submission.setStatus(SubmissionStatus.RUNNING);
        submissionRepository.save(submission);
        EvaluationResult evaluationResult;
        try
        {
            evaluationResult = submissionEvaluatorService.evaluate(submission.getProblem(),submission.getLanguage(),submission.getSourceCode());
            submission.setStatus(SubmissionStatus.COMPLETED);
            applyResult(evaluationResult,submission);
            submissionRepository.save(submission);
        }
        catch (Exception e)
        {
            submission.setStatus(SubmissionStatus.FAILED);
            submission.setVerdict(Verdict.SYSTEM_ERROR);
            submission.setErrorMessage(e.getMessage());
            submissionRepository.save(submission);
        }
    }

    private void applyTestCasesData(List<TestCase> testCases,StringBuilder input)
    {
        input.append(testCases.size());
        input.append("\n");
        for(TestCase testCase : testCases)
        {
            input.append(testCase.getInputData());
            input.append("\n");
        }
    }

    @Override
    @Async
    public void processBatchSubmissions(List<Long> submissionIds) {
        List<Submission> submissions = submissionRepository.findAllById(submissionIds);
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
            StringBuilder input = new StringBuilder();
            applyTestCasesData(testCases,input);
            submissionsToJudge.add(new JudgeDTO(finalCode,language,input.toString()));
        }
        Judge0TokenResponse[] tokens = judgeService.executeBatch(submissionsToJudge);
        for(int i=0;i<tokens.length;i++)
        {
            submissions.get(i).setJudge0Token(tokens[i].getToken());
            submissionRepository.save(submissions.get(i));
        }
    }
}
