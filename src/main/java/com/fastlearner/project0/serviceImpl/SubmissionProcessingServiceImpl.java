package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.evaluation.EvaluationResult;
import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.enums.SubmissionStatus;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.SubmissionRepository;
import com.fastlearner.project0.service.SubmissionEvaluatorService;
import com.fastlearner.project0.service.SubmissionProcessingService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Service
public class SubmissionProcessingServiceImpl implements SubmissionProcessingService {
    private final SubmissionEvaluatorService submissionEvaluatorService;
    private final SubmissionRepository submissionRepository;
    public SubmissionProcessingServiceImpl(SubmissionEvaluatorService submissionEvaluatorService, SubmissionRepository submissionRepository) {
        this.submissionEvaluatorService = submissionEvaluatorService;
        this.submissionRepository = submissionRepository;
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
            Thread.sleep(10000);
        }
        catch (Exception e)
        {
            submission.setStatus(SubmissionStatus.FAILED);
            submission.setVerdict(Verdict.RUNTIME_ERROR);
            submission.setErrorMessage(e.getMessage());
            submissionRepository.save(submission);
        }


    }
}
