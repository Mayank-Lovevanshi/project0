package com.fastlearner.project0.serviceImpl.job;

import com.fastlearner.project0.dto.job.Job;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.dto.submission.CreateSubmissionRequest;
import com.fastlearner.project0.dto.submission.SubmissionResponse;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.serviceImpl.util.SourceCodeFactory;
import com.fastlearner.project0.serviceImpl.util.TestcaseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
@RequiredArgsConstructor
@Component
public class SubmissionJobFactory
{
    private final SourceCodeFactory sourceCodeFactory;
    private final TestcaseFactory testcaseFactory;
    private final JudgeService judgeService;
    @Value("${judge.api.run.callbackUrl}")
    private final String callbackUrl;
    public Job<SubmissionResponse> createJob(CreateSubmissionRequest request)
    {
        String finalCode = sourceCodeFactory.getSourceCode(
                request.getProblemId(),
                request.getLanguage(),
                request.getSourceCode()
        );
        String stdin = testcaseFactory.buildTestcases(request.getProblemId(), TestCaseType.HIDDEN);
        JudgeRequest judgeRequest = new JudgeRequest(finalCode,
                judgeService.getJudgeLanguageId(request.getLanguage()),
                stdin,
                callbackUrl);
        return new Job<>(judgeRequest,new CompletableFuture<>());
    }

}
