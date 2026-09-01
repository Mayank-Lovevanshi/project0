package com.fastlearner.project0.serviceImpl.judge;

import com.fastlearner.project0.dto.run.TestCaseResultDTO;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.service.judge.EvaluateJudgeResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluateJudge0Response implements EvaluateJudgeResponse
{
    @Override
    public int countPassedTestCases(List<TestCase> testCases, String actualOutput)
    {
        if(actualOutput == null) return 0;
        String[] actualOutputs = actualOutput.split("---FASTELEARNER_DELIMITER_42---\n?");
        
        int passedTestCases = 0;
        int n = Math.min(testCases.size(), actualOutputs.length);
        
        for(int i = 0; i < n; i++) {
            if(normalize(testCases.get(i).getExpectedOutput()).equals(normalize(actualOutputs[i]))) {
                passedTestCases++;
            } else {
                break;
            }
        }
        return passedTestCases;
    }

    @Override
    public List<TestCaseResultDTO> evaluateRun(List<TestCase> testCases, String actualOutput)
    {
        String[] actualOutputs = actualOutput == null ? new String[0] : actualOutput.split("---FASTELEARNER_DELIMITER_42---\n?");
        List<TestCaseResultDTO> testCaseResults = new ArrayList<>();

        for (int i = 0; i < testCases.size(); i++) {
            TestCase tc = testCases.get(i);
            TestCaseResultDTO result = new TestCaseResultDTO();
            result.setTestCaseId(tc.getId());
            result.setSequenceNumber(tc.getSequenceNumber());
            result.setInput(tc.getInputData());
            result.setExpectedOutput(tc.getExpectedOutput());
            
            String actOut = i < actualOutputs.length ? actualOutputs[i].trim() : "";
            result.setActualOutput(actOut);
            
            boolean passed = normalize(tc.getExpectedOutput()).equals(normalize(actOut));
            result.setPassed(passed);
            
            testCaseResults.add(result);
        }
        return testCaseResults;
    }

    private String normalize(String s)
    {
        if (s == null) return "";
        return s
                .replace("\r\n", "\n")
                .replace("\r", "\n")
                .trim();
    }
}
