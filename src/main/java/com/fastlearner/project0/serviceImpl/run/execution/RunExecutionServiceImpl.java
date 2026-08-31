package com.fastlearner.project0.serviceImpl.run.execution;
import com.fastlearner.project0.dto.job.RunJob;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.service.run.execution.RunExecutionService;
import com.fastlearner.project0.service.run.queue.RunExecutionQueue;
import com.fastlearner.project0.service.run.registery.PendingRunExecutionRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RunExecutionServiceImpl implements RunExecutionService
{
    private final RunExecutionQueue executionQueue;
    private final JudgeService judgeService;
    private final PendingRunExecutionRegistry pendingExecutionRegistry;
    public void execute() throws InterruptedException {
        // 1. Take the first job. This blocks until at least one request is available.
        RunJob firstJob = executionQueue.take();

        List<RunJob> jobs = new ArrayList<>();
        jobs.add(firstJob);

        long deadline = System.currentTimeMillis() + 250; // Wait up to 250ms total for more requests

        // 2. Keep collecting jobs until we hit a batch size of 10 or run out of time
        while (jobs.size() < 10) {
            long remainingTime = deadline - System.currentTimeMillis();
            if (remainingTime <= 0) {
                break; // Time window has expired
            }

            // Poll the queue for the remaining duration of our 250ms window
            RunJob nextJob = executionQueue.poll();
            if (nextJob != null) {
                jobs.add(nextJob);
            } else {
                // poll returned null, meaning no new requests arrived within the timeout
                break;
            }
        }

        // 3. Process the collected batch (could be anywhere from 1 to 10 jobs)
        List<JudgeRequest> judgeRequests = jobs.stream().map(RunJob::getJudgeRequest).toList();
        Judge0TokenResponse[] tokens = judgeService.execute(judgeRequests);

        for (int i = 0; i < tokens.length; i++) {
            pendingExecutionRegistry.put(tokens[i].getToken(), jobs.get(i));
        }
    }
}
