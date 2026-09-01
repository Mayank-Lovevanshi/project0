package com.fastlearner.project0.service.judge;

import com.fastlearner.project0.dto.run.TestCaseResultDTO;
import com.fastlearner.project0.entity.TestCase;
import java.util.List;

public interface EvaluateJudgeResponse
{
    public int countPassedTestCases(List<TestCase> testCases, String actualOutput);
    public List<TestCaseResultDTO> evaluateRun(List<TestCase> testCases, String actualOutput);
}
