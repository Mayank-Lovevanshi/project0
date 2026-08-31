package com.fastlearner.project0.serviceImpl.job;

import com.fastlearner.project0.dto.job.RunJob;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.dto.run.RunRequestDTO;
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
    public RunJob createJob(RunRequestDTO runRequest)
    {
        String driverCode = codeGeneratorService.generateDriverCode(runRequest.getProblemId(), runRequest.getLanguage());
        String finalCode = driverCode+"\n"+runRequest.getSourceCode();
        String stdin = testcaseFactory.buildTestcases(runRequest.getProblemId(), TestCaseType.SAMPLE);
        JudgeRequest judgeRequest = new JudgeRequest(finalCode,
                                        judgeService.getJudgeLanguageId(runRequest.getLanguage()),
                                        stdin,
                                            callbackUrl);
        return new RunJob(runRequest.getProblemId(), judgeRequest,new CompletableFuture<>());
    }

}
