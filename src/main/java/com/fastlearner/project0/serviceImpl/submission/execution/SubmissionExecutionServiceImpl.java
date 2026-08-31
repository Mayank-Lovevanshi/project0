package com.fastlearner.project0.serviceImpl.submission.execution;
import com.fastlearner.project0.dto.job.SubmissionJob;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.service.submission.execution.SubmissionExecutionService;
import com.fastlearner.project0.service.submission.queue.SubmissionExecutionQueue;
import com.fastlearner.project0.service.submission.registery.PendingSubmissionExecutionRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubmissionExecutionServiceImpl implements SubmissionExecutionService
{
    private final SubmissionExecutionQueue executionQueue;
    private final JudgeService judgeService;
    private final PendingSubmissionExecutionRegistry pendingExecutionRegistry;
    public void execute() throws InterruptedException {

    }
}
