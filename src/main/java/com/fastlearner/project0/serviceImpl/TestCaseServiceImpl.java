package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.testcases.CreateTestCaseRequest;
import com.fastlearner.project0.dto.testcases.TestCaseResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.exceptions.InvalidArgumentException;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.ProblemRepository;
import com.fastlearner.project0.repository.TestCaseRepository;
import com.fastlearner.project0.service.TestCaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TestCaseServiceImpl implements TestCaseService
{
    private final TestCaseRepository testCaseRepository;
    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper;
    public TestCaseServiceImpl(TestCaseRepository testCaseRepository, ProblemRepository problemRepository, ModelMapper modelMapper) {
        this.testCaseRepository = testCaseRepository;
        this.problemRepository = problemRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public TestCaseResponse createTestCase(Long problemId, CreateTestCaseRequest testCaseDTO) {
        if(problemId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID");
        Problem problemfromDB = problemRepository.findById(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND"));
        TestCase testCase = modelMapper.map(testCaseDTO, TestCase.class);
        testCase.setProblem(problemfromDB);
        testCaseRepository.save(testCase);
        return modelMapper.map(testCase, TestCaseResponse.class);
    }

    @Override
    public List<TestCaseResponse> getSampleTestCases(Long problemId) {
        if(problemId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID");
        List<TestCaseResponse> sampleTestCases = new ArrayList<>();
        List<TestCase> allTestCases = testCaseRepository.findByProblemId(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND"));
        for(TestCase testCase : allTestCases){
            if(testCase.getTestCaseType()== TestCaseType.SAMPLE){
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
}
