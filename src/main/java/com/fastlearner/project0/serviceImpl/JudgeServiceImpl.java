package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.JudgeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeServiceImpl implements JudgeService
{
    // create a file with all the source code
    // compile the program
    // execute program for each testcase 
    @Override
    public Submission execute(String sourceCode, Language language, List<TestCase> testCases) {
        return null;
    }
}
