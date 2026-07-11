package com.fastlearner.project0.service.judge;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.enums.Language;

import java.util.List;

public interface JudgeService
{
    public Integer getJudgeLanguageId(Language language);
    public Judge0TokenResponse[] execute(List<JudgeRequest> requests);
}
