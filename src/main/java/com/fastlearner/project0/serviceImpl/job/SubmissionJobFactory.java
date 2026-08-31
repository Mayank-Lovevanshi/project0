package com.fastlearner.project0.serviceImpl.job;

import com.fastlearner.project0.dto.job.SubmissionJob;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.dto.submission.CreateSubmissionRequest;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.service.codeGenerator.CodeGeneratorService;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.serviceImpl.util.TestcaseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
@RequiredArgsConstructor
@Component
public class SubmissionJobFactory
{
    private final CodeGeneratorService codeGeneratorService;
    private final TestcaseFactory testcaseFactory;
    private final JudgeService judgeService;
    @Value("${judge0.api.callbackUrl}")
    private String callbackUrl;
    public SubmissionJob createJob(CreateSubmissionRequest request,Long submissionId)
    {
        String driverCode = codeGeneratorService.generateDriverCode(
                request.getProblemId(),
                request.getLanguage()
        );
        String finalCode = driverCode+"\n"+request.getSourceCode();
        String stdin = testcaseFactory.buildTestcases(request.getProblemId(), TestCaseType.HIDDEN);
        JudgeRequest judgeRequest = new JudgeRequest(finalCode,
                judgeService.getJudgeLanguageId(request.getLanguage()),
                stdin,
                callbackUrl);
        return new SubmissionJob(judgeRequest, new CompletableFuture<>(),submissionId, request.getProblemId());
    }

}
