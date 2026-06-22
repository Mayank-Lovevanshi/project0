package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.judge.JudgeDTO;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.dto.judge0.JudgeResult;
import com.fastlearner.project0.enums.Language;

import java.util.List;

public interface JudgeService
{
    public String execute(String sourceCode, Language language,String input);
    public JudgeResult getResult(String token);
    public Integer getJudge0LanguageId(Language language);
    public JudgeResult convertToJudgeResult(Judge0SubmissionResponse body);
    public Judge0TokenResponse[] executeBatch(List<JudgeDTO> submissionsToJudge);
}
