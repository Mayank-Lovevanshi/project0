package com.fastlearner.project0.dto.job;

import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.dto.submission.SubmissionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.CompletableFuture;
@Data
@AllArgsConstructor
public class SubmissionJob
{
    private JudgeRequest judgeRequest;
    private CompletableFuture<SubmissionResponse> future;
    private Long submissionId;
    private Long problemId;
}
