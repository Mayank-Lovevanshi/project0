package com.fastlearner.project0.serviceImpl.util;

import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
@Component
@RequiredArgsConstructor
public class TestcaseFileGenerator
{
    private final TestCaseRepository testCaseRepository;
    public void generateTestcaseFile(Long problemId, TestCaseType  testCaseType)
    {
        List<TestCase> testCases = testCaseRepository.findByProblemIdAndTestCaseType(problemId,testCaseType);
        String filePath = "../../../../resources/static/testcase.txt";
        StringBuilder fileData = new StringBuilder();
        fileData.append(testCases.size());
        for(TestCase testCase : testCases)
        {
            fileData.append(testCase.getSequenceNumber()).append("\n");
            fileData.append(testCase.getInputData()).append("\n");
            fileData.append(testCase.getExpectedOutput()).append("\n");
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));)
        {
            writer.write(fileData.toString());
        } catch (IOException e) {
            System.out.println("Error writing to file  TestcaseFileGenerator"+e.getMessage());
        }

    }
}
