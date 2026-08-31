package com.fastlearner.project0.service.testcases;

import com.fastlearner.project0.dto.testcases.CreateTestCaseBatchRequest;
import com.fastlearner.project0.dto.testcases.TestCaseBatchResponse;
import com.fastlearner.project0.dto.testcases.CreateTestCaseRequest;
import com.fastlearner.project0.dto.testcases.TestCaseResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.TestCaseType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TestCaseService
{

     TestCaseResponse createTestCase(Long problemId, CreateTestCaseRequest testCaseDTO);
     List<TestCaseResponse> getSampleTestCases(Long problemId);
     List<TestCaseResponse> getAllTestCases(Long problemId);
     String deleteTestCase(Long testCaseId);
     TestCaseBatchResponse createTestCaseBatch(Long problemId, CreateTestCaseBatchRequest testCaseBatchDTO);
     List<TestCase> getTestCases(Long problemId, TestCaseType testCaseType);
}
