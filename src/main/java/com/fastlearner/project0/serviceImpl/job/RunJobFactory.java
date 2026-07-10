package com.fastlearner.project0.serviceImpl.job;

import com.fastlearner.project0.dto.job.Job;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.dto.run.RunRequestDTO;
import com.fastlearner.project0.dto.run.RunResponseDTO;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.serviceImpl.util.SourceCodeFactory;
import com.fastlearner.project0.serviceImpl.util.TestcaseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
@Component
@RequiredArgsConstructor
public class RunJobFactory
{
    private final SourceCodeFactory sourceCodeFactory;
    private final TestcaseFactory testcaseFactory;
    private final JudgeService judgeService;
    @Value("${judge.api.run.callbackUrl}")
    private final String callbackUrl;
    public Job<RunResponseDTO> createJob(RunRequestDTO runRequest)
    {
        String finalCode = sourceCodeFactory.getSourceCode(
                                                runRequest.getProblemId(),
                                                runRequest.getLanguage(),
                                                runRequest.getSourceCode()
        );
        String stdin = testcaseFactory.buildTestcases(runRequest.getProblemId(), TestCaseType.SAMPLE);
        JudgeRequest judgeRequest = new JudgeRequest(finalCode,
                                        judgeService.getJudgeLanguageId(runRequest.getLanguage()),
                                        stdin,
                                        callbackUrl);
        return new Job<>(judgeRequest,new CompletableFuture<>());
    }

}
