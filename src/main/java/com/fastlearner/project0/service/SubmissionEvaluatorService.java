package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.evaluation.EvaluationResult;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.enums.Language;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SubmissionEvaluatorService
{
    public void evaluateResponse(Judge0SubmissionResponse body);
}
