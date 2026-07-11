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
        List<SubmissionJob> jobs = new ArrayList<>();
        while(!executionQueue.isEmpty())
        {
            jobs.add(executionQueue.take());
        }
        List<JudgeRequest> judgeRequests = jobs.stream().map(job -> job.getJudgeRequest()).toList();
        Judge0TokenResponse[] tokens = judgeService.execute(judgeRequests);
        for(int i=0;i<tokens.length;i++)
        {
            pendingExecutionRegistry.put(tokens[i].getToken(),jobs.get(i));
        }
    }
}
