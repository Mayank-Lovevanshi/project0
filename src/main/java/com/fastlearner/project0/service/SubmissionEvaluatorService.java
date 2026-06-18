package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.evaluation.EvaluationResult;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.enums.Language;

public interface SubmissionEvaluatorService
{
    EvaluationResult evaluate(Problem problem, Language language, String sourceCode);
}
