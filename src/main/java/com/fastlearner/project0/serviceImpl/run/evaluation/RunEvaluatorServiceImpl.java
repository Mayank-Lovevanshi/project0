package com.fastlearner.project0.serviceImpl.run.evaluation;

import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.service.run.evaluation.RunEvaluatorService;
import com.fastlearner.project0.service.run.registery.PendingRunExecutionRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RunEvaluatorServiceImpl implements RunEvaluatorService {
    private final PendingRunExecutionRegistry pendingRunExecutionRegistry;

    @Override
    public void evaluate(Judge0SubmissionResponse body) {
        String token =  body.getToken();

    }
}
