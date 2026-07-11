package com.fastlearner.project0.serviceImpl.job;

import com.fastlearner.project0.dto.job.Job;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.dto.run.RunRequestDTO;
import com.fastlearner.project0.dto.run.RunResponseDTO;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.service.codeGenerator.CodeGeneratorService;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.serviceImpl.util.TestcaseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
@Component
@RequiredArgsConstructor
public class RunJobFactory
{
    private final CodeGeneratorService codeGeneratorService;
    private final TestcaseFactory testcaseFactory;
    private final JudgeService judgeService;
    @Value("${judge.api.run.callbackUrl}")
    private String callbackUrl;

    public Job<RunResponseDTO> createJob(RunRequestDTO runRequest)
    {
        StringBuilder driverCode = codeGeneratorService.generateDriverCode(runRequest.getProblemId(), runRequest.getLanguage());
        String finalCode = driverCode.append("\n").append(runRequest.getSourceCode()).toString();
        String stdin = testcaseFactory.buildTestcases(runRequest.getProblemId(), TestCaseType.SAMPLE).getStdin();
        JudgeRequest judgeRequest = new JudgeRequest(finalCode,
                                        judgeService.getJudgeLanguageId(runRequest.getLanguage()),
                                        stdin,
                                        callbackUrl);
        return new Job<>(judgeRequest,new CompletableFuture<>());
    }

}
