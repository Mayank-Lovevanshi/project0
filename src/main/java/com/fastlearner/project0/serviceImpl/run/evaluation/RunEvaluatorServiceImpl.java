package com.fastlearner.project0.serviceImpl.run.evaluation;

import com.fastlearner.project0.dto.job.RunJob;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.dto.testcases.factory.TestcaseFactoryResponse;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.service.judge.EvaluateJudgeResponse;
import com.fastlearner.project0.service.run.evaluation.RunEvaluatorService;
import com.fastlearner.project0.service.run.registery.PendingRunExecutionRegistry;
import com.fastlearner.project0.serviceImpl.util.TestcaseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RunEvaluatorServiceImpl implements RunEvaluatorService {
    private final PendingRunExecutionRegistry pendingRunExecutionRegistry;
    private final TestcaseFactory testcaseFactory;
    private final EvaluateJudgeResponse evaluateJudgeResponse;
    public void evaluate(Judge0SubmissionResponse body)
    {
        RunJob job = pendingRunExecutionRegistry.get(body.getToken());
        pendingRunExecutionRegistry.remove(body.getToken());
        TestcaseFactoryResponse factoryResponse = testcaseFactory.buildTestcases(job.getProblemId(), TestCaseType.HIDDEN);
        int totalTestcases = factoryResponse.getNumberOfTestCases();
        int passedTestcases = evaluateJudgeResponse.getPassedTestCases(factoryResponse.getStdin(), body.getStdout());

    }
}
