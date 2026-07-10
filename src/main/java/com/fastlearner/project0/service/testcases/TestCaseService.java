package com.fastlearner.project0.service.testcases;

import com.fastlearner.project0.dto.testcases.CreateTestCaseBatchRequest;
import com.fastlearner.project0.dto.testcases.TestCaseBatchResponse;
import com.fastlearner.project0.dto.testcases.CreateTestCaseRequest;
import com.fastlearner.project0.dto.testcases.TestCaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TestCaseService
{

    public TestCaseResponse createTestCase(Long problemId, CreateTestCaseRequest testCaseDTO);
    public List<TestCaseResponse> getSampleTestCases(Long problemId);
    public List<TestCaseResponse> getAllTestCases(Long problemId);
    public String deleteTestCase(Long testCaseId);
    public TestCaseBatchResponse createTestCaseBatch(Long problemId, CreateTestCaseBatchRequest testCaseBatchDTO);
}
