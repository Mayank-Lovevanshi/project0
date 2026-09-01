package com.fastlearner.project0.serviceImpl.submission.evaluation;

import com.fastlearner.project0.dto.job.SubmissionJob;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.dto.submission.SubmissionResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.SubmissionStatus;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.service.judge.EvaluateJudgeResponse;
import com.fastlearner.project0.service.submission.SubmissionService;
import com.fastlearner.project0.service.submission.evaluation.SubmissionEvaluatorService;
import com.fastlearner.project0.service.submission.registery.PendingSubmissionExecutionRegistry;
import com.fastlearner.project0.service.testcases.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubmissionEvaluatorServiceImpl implements SubmissionEvaluatorService
{
    private final TestCaseService testCaseService;
    private final SubmissionService submissionService;
    private final PendingSubmissionExecutionRegistry pendingSubmissionExecutionRegistry;
    private final EvaluateJudgeResponse evaluateJudgeResponse;
    private final ModelMapper modelMapper;

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
    
    private void modifySubmission(Judge0SubmissionResponse result, List<TestCase> testCases, Submission submission)
    {
        int passedTestCases=0;
        String actualOutput = result.getStdout();
        Verdict judgeVerdict = mapVerdict(result);
        passedTestCases = evaluateJudgeResponse.countPassedTestCases(testCases, actualOutput);
        submission.setVerdict(judgeVerdict);
        if(judgeVerdict.equals(Verdict.ACCEPTED))
        {
            Verdict verdict = passedTestCases == testCases.size() ? Verdict.ACCEPTED : Verdict.WRONG_ANSWER;
            submission.setVerdict(verdict);
        }
        if(judgeVerdict.equals(Verdict.COMPILATION_ERROR))
        {
            submission.setErrorMessage(result.getCompileOutput());
        }
        else if(judgeVerdict.equals(Verdict.RUNTIME_ERROR))
        {
            submission.setErrorMessage(result.getStderr());
        }
        else
        {
            submission.setErrorMessage(result.getMessage());
        }
        submission.setPassedTestCases(passedTestCases);
        submission.setExecutionTimeMs(result.getTime()==null?"0":result.getTime());
        submission.setMemoryUsedKb(result.getMemory()==null?0:result.getMemory());
        if(judgeVerdict.equals(Verdict.RUNTIME_ERROR))
        {
            if(submission.getMemoryUsedKb()>=128000) submission.setVerdict(Verdict.MEMORY_LIMIT_EXCEEDED);
        }
        submission.setTotalTestCases(testCases.size());
        submission.setStatus(SubmissionStatus.COMPLETED);
        submission.setCompletedAt(LocalDateTime.now());
    }

    @Override
    public void evaluateResponse(Judge0SubmissionResponse response) {
        String token = response.getToken();
        SubmissionJob job = pendingSubmissionExecutionRegistry.get(token);
        Submission submission = submissionService.findSubmissionById(job.getSubmissionId());
        Problem problem = submission.getProblem();
        
        List<TestCase> testCases = testCaseService.getTestCases(problem.getId(), TestCaseType.HIDDEN);
            
        modifySubmission(response, testCases, submission);
        long totalTimeMs = Duration.between(submission.getSubmittedAt(), submission.getCompletedAt()).toSeconds();
        System.out.println("Total time: " + totalTimeMs+"s");
        pendingSubmissionExecutionRegistry.remove(token);
        job.getFuture().complete(modelMapper.map(submission, SubmissionResponse.class));
        submissionService.saveSubmission(submission);
    }
}
