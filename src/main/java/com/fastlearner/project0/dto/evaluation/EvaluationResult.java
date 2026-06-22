package com.fastlearner.project0.dto.evaluation;

import com.fastlearner.project0.enums.Verdict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationResult
{
    private Verdict verdict;
    private Integer passedTestCases;
    private Integer totalTestCases;
    private String executionTimeMs;
    private Integer memoryUsedKb;
    private String errorMessage;
}
