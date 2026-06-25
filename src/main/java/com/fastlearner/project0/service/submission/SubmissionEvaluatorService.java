package com.fastlearner.project0.service.submission;

import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;

public interface SubmissionEvaluatorService
{
    public void evaluateResponse(Judge0SubmissionResponse body);
}
