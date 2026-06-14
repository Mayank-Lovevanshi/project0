package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.testcases.CreateTestCaseRequest;
import com.fastlearner.project0.dto.testcases.TestCaseResponse;
import com.fastlearner.project0.service.TestCaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestCaseController
{
    private final TestCaseService testCaseService;
    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }
    @PostMapping("/problems/{problemId}/testcases")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TestCaseResponse> createTestCase(@PathVariable Long problemId, @RequestBody @Valid CreateTestCaseRequest testCaseDTO)
    {
        return new ResponseEntity<>(testCaseService.createTestCase(problemId,testCaseDTO), HttpStatus.CREATED);
    }
    @GetMapping("/problems/{problemId}/sample-testcases")
    public ResponseEntity<List<TestCaseResponse>> getSampleTestCases(@PathVariable Long problemId)
    {
        return new ResponseEntity<>(testCaseService.getSampleTestCases(problemId),HttpStatus.OK);
    }
    @GetMapping("/problems/{problemId}/testcases")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TestCaseResponse>> getAllTestCases(@PathVariable Long problemId)
    {
        return new ResponseEntity<>(testCaseService.getAllTestCases(problemId),HttpStatus.OK);
    }
    @DeleteMapping("/api/testcases/{testCaseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>  deleteTestCase(@PathVariable Long testCaseId)
    {
        return new ResponseEntity<>(testCaseService.deleteTestCase(testCaseId),HttpStatus.OK);
    }
}
