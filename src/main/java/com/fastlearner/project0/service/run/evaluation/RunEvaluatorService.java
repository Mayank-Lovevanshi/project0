package com.fastlearner.project0.service.run.evaluation;

import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;

public interface RunEvaluatorService
{
    public void evaluate(Judge0SubmissionResponse body);
}
