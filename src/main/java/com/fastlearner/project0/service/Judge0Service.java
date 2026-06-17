package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.dto.judge0.JudgeResult;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.Language;

import java.util.List;

public interface Judge0Service
{
    public JudgeResult execute(String sourceCode, Language language, String input);
    public Integer getJudge0LanguageId(Language language);
    public JudgeResult convertToJudgeResult(Judge0SubmissionResponse body);
    List<JudgeResult> executeBatch(String sourceCode, Language language, List<TestCase> testCases);
}
