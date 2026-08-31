package com.fastlearner.project0.serviceImpl.testcases;

import com.fastlearner.project0.dto.testcases.CreateTestCaseBatchRequest;
import com.fastlearner.project0.dto.testcases.CreateTestCaseRequest;
import com.fastlearner.project0.dto.testcases.TestCaseBatchResponse;
import com.fastlearner.project0.dto.testcases.TestCaseResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.exceptions.InvalidArgumentException;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.TestCaseRepository;
import com.fastlearner.project0.service.problem.ProblemService;
import com.fastlearner.project0.service.testcases.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService
{
    private final TestCaseRepository testCaseRepository;
    private final ProblemService problemService;
    private final ModelMapper modelMapper;
    @Override
    public TestCaseResponse createTestCase(Long problemId, CreateTestCaseRequest testCaseDTO) {
        if(problemId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID");
        Problem problemDB = problemService.findById(problemId);
        TestCase testCase = modelMapper.map(testCaseDTO, TestCase.class);
        testCase.setProblem(problemDB);
        testCaseRepository.save(testCase);
        return modelMapper.map(testCase, TestCaseResponse.class);
    }

    @Override
    public List<TestCaseResponse> getSampleTestCases(Long problemId) {
        if(problemId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID");
        List<TestCaseResponse> sampleTestCases = new ArrayList<>();
        List<TestCase> allTestCases = testCaseRepository.findByProblemId(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND"));
        for(TestCase testCase : allTestCases){
            if(testCase.getTestCaseType()==TestCaseType.SAMPLE){
                sampleTestCases.add(modelMapper.map(testCase, TestCaseResponse.class));
            }
        }
        return sampleTestCases;
    }

    @Override
    public List<TestCaseResponse> getAllTestCases(Long problemId) {
        List<TestCaseResponse> allTestCases = new ArrayList<>();
        List<TestCase> testCasesFromDB = testCaseRepository.findByProblemId(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND"));
        for(TestCase testCase : testCasesFromDB){
            allTestCases.add(modelMapper.map(testCase, TestCaseResponse.class));
        }
        return allTestCases;
    }

    @Override
    public String deleteTestCase(Long testCaseId) {
        if(testCaseId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID");
        TestCase testCase = testCaseRepository.findById(testCaseId).orElseThrow(()->new ResourceNotFoundException("TEST_CASE_NOT_FOUND"));
        testCaseRepository.delete(testCase);
        return "DELETED";
    }

    @Override
    public TestCaseBatchResponse createTestCaseBatch(Long problemId, CreateTestCaseBatchRequest testCaseBatchDTO) {
        Problem problem = problemService.findById(problemId);
        List<CreateTestCaseRequest> list= testCaseBatchDTO.getTestCases();
        TestCase testCaseDB;
        TestCase savedTestCaseDB;
        TestCaseBatchResponse testCaseBatchResponse = new TestCaseBatchResponse();
        List<TestCaseResponse> testCaseResponses = new ArrayList<>();
        for(CreateTestCaseRequest testCase : list) {
            testCaseDB = modelMapper.map(testCase, TestCase.class);
            testCaseDB.setProblem(problem);
            savedTestCaseDB =  testCaseRepository.save(testCaseDB);
            testCaseResponses.add(modelMapper.map(savedTestCaseDB, TestCaseResponse.class));
        }
        testCaseBatchResponse.setTestCases(testCaseResponses);
        return testCaseBatchResponse;
    }

    @Override
    public List<TestCase> getTestCases(Long problemId, TestCaseType testCaseType) {
        return testCaseRepository.findByProblemIdAndTestCaseType(problemId,testCaseType);
    }
}
