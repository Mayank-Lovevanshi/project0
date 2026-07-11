package com.fastlearner.project0.serviceImpl.util;

import com.fastlearner.project0.dto.testcases.factory.TestcaseFactoryResponse;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
@RequiredArgsConstructor
public class TestcaseFactory
{
    private final TestCaseRepository testCaseRepository;
    public TestcaseFactoryResponse buildTestcases(Long problemId, TestCaseType testcaseType) {
        List<TestCase> testcases = testCaseRepository.findByProblemIdAndTestCaseType(problemId,testcaseType);
        StringBuilder input = new StringBuilder();
        for(TestCase testcase : testcases){
            if(testcase.getTestCaseType().equals(testcaseType)){
                input.append(testcase.getExpectedOutput()).append("\n");
            }
        }
        return new TestcaseFactoryResponse(input.toString(),testcases.size());
    }
}
