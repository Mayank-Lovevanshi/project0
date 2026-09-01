package com.fastlearner.project0.serviceImpl.run.evaluation;

import com.fastlearner.project0.dto.error.ErrorDetails;
import com.fastlearner.project0.dto.job.RunJob;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.dto.run.RunResponseDTO;
import com.fastlearner.project0.dto.run.TestCaseResultDTO;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.ErrorType;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.service.judge.EvaluateJudgeResponse;
import com.fastlearner.project0.service.run.evaluation.RunEvaluatorService;
import com.fastlearner.project0.service.run.registery.PendingRunExecutionRegistry;
import com.fastlearner.project0.service.testcases.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RunEvaluatorServiceImpl implements RunEvaluatorService {
    private final PendingRunExecutionRegistry pendingRunExecutionRegistry;
    private final TestCaseService testCaseService;
    private final EvaluateJudgeResponse evaluateJudgeResponse;

    @Override
    public void evaluate(Judge0SubmissionResponse body) {
        String token = body.getToken();
        RunJob job = pendingRunExecutionRegistry.get(token);
        if (job == null) return;

        RunResponseDTO response = new RunResponseDTO();
        response.setExecutionTimeMs(body.getTime() == null ? 0.0 : Double.parseDouble(body.getTime()));
        response.setMemoryUsedKb(body.getMemory() == null ? 0 : body.getMemory());

        Verdict judgeVerdict = mapVerdict(body);
        List<TestCase> sampleTestCases = testCaseService.getTestCases(job.getProblemId(), TestCaseType.SAMPLE);
        response.setTotalTestCases(sampleTestCases.size());

        if (judgeVerdict == Verdict.COMPILATION_ERROR) {
            response.setOverallVerdict(Verdict.COMPILATION_ERROR);
            response.setPassedTestCases(0);
            response.setErrorDetails(new ErrorDetails(ErrorType.COMPILATION_ERROR, body.getCompileOutput()));
            response.setTestCaseResults(new ArrayList<>());
        } else if (judgeVerdict == Verdict.RUNTIME_ERROR) {
            response.setOverallVerdict(Verdict.RUNTIME_ERROR);
            response.setPassedTestCases(0);
            response.setErrorDetails(new ErrorDetails(ErrorType.RUNTIME_ERROR, body.getStderr()));
            response.setTestCaseResults(new ArrayList<>());
        } else if (judgeVerdict == Verdict.TIME_LIMIT_EXCEEDED || judgeVerdict == Verdict.SYSTEM_ERROR) {
             response.setOverallVerdict(judgeVerdict);
             response.setPassedTestCases(0);
             response.setErrorDetails(new ErrorDetails(ErrorType.INTERNAL_ERROR, body.getMessage()));
             response.setTestCaseResults(new ArrayList<>());
        } else {
            // ACCEPTED by judge0 means code ran successfully without crashing.
            List<TestCaseResultDTO> testCaseResults = evaluateJudgeResponse.evaluateRun(sampleTestCases, body.getStdout());
            int passedCount = 0;
            for (TestCaseResultDTO result : testCaseResults) {
                if (result.getPassed()) passedCount++;
            }

            response.setPassedTestCases(passedCount);
            response.setTestCaseResults(testCaseResults);
            if (passedCount == sampleTestCases.size()) {
                response.setOverallVerdict(Verdict.ACCEPTED);
            } else {
                response.setOverallVerdict(Verdict.WRONG_ANSWER);
            }
        }
        System.out.println(response);
        pendingRunExecutionRegistry.remove(token);
        job.getFuture().complete(response);
    }

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
}
