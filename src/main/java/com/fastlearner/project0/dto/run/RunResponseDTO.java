package com.fastlearner.project0.dto.run;

import com.fastlearner.project0.dto.error.ErrorDetails;
import com.fastlearner.project0.enums.Verdict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunResponseDTO
{
    private Verdict overallVerdict;
    private Double executionTimeMs;
    private Integer memoryUsedKb;
    private Integer passedTestCases;
    private Integer totalTestCases;
    private ErrorDetails errorDetails;
    private List<TestCaseResultDTO> testCaseResults;
}
