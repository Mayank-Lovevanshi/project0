package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.judge.JudgeDTO;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.enums.Language;

import java.util.List;

public interface JudgeService
{
    public Integer getJudge0LanguageId(Language language);
    public Judge0TokenResponse[] executeBatch(List<JudgeDTO> submissionsToJudge);
}
