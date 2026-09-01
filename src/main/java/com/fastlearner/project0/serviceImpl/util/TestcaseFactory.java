package com.fastlearner.project0.serviceImpl.util;

import com.fastlearner.project0.dto.testcases.TestcaseFactoryResponse;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.service.testcases.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
@RequiredArgsConstructor
public class TestcaseFactory
{
    private final TestCaseService testCaseService;
    public String buildTestcases(Long problemId, TestCaseType testcaseType) {
        List<TestCase> testcases = testCaseService.getTestCases(problemId,testcaseType);
        StringBuilder input = new StringBuilder();
        input.append(testcases.size()).append("\n");
        for(TestCase testcase : testcases){
            input.append(testcase.getInputData()).append("\n");
        }
        return input.toString();
    }
    public TestcaseFactoryResponse getExpectedOutput(Long problemId, TestCaseType testcaseType) {
        List<TestCase> testcases = testCaseService.getTestCases(problemId,testcaseType);
        StringBuilder expectedOutput = new StringBuilder();
        for(TestCase testcase : testcases){
            expectedOutput.append(testcase.getExpectedOutput()).append("\n");
        }
        TestcaseFactoryResponse response = new TestcaseFactoryResponse(testcases.size(),expectedOutput.toString());
        return response;
    }
}
