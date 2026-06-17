package com.fastlearner.project0.service;

import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.Language;

import java.util.List;

public interface JudgeService
{
    public Submission execute(String sourceCode, Language language, List<TestCase> testCases);
}
