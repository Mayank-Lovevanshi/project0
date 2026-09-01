package com.fastlearner.project0.serviceImpl.run.worker;

import com.fastlearner.project0.dto.job.RunJob;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.service.run.queue.RunExecutionQueue;
import com.fastlearner.project0.service.run.registery.PendingRunExecutionRegistry;
import com.fastlearner.project0.service.worker.ExecutionWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import java.util.List;


@Component
@RequiredArgsConstructor
public class RunExecutionWorker implements ExecutionWorker {
    private final JudgeService judgeService;
    private final RunExecutionQueue runExecutionQueue;
    private final PendingRunExecutionRegistry pendingRunExecutionRegistry;
    private volatile boolean isRunning;

    @Override
    public void run() {
        isRunning = true;
        while (!Thread.currentThread().isInterrupted()) {
            List<RunJob> jobs = new ArrayList<>();
            try {
                // Block until at least one job is available
                RunJob firstJob = runExecutionQueue.take();
                jobs.add(firstJob);
                
                // Drain up to 19 more jobs if available immediately
                while (!runExecutionQueue.isEmpty() && jobs.size() < 20) {
                    RunJob job = runExecutionQueue.poll();
                    if (job == null) break;
                    jobs.add(job);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                continue;
            }
            if (jobs.isEmpty()) continue;
            List<JudgeRequest> requestList = jobs
                    .stream()
                    .map(RunJob::getJudgeRequest).toList();
            System.out.println("RunWorker is call Judge Service");
            Judge0TokenResponse[] responses = judgeService.execute(requestList);
            for (int i = 0; i < responses.length; i++) {
                pendingRunExecutionRegistry.put(responses[i].getToken(), jobs.get(i));
            }
        }
    }
    public void stop()
    {
        isRunning = false;
    }
}

/*
Responsibility of worker thread
1. pick the jobs to run from the queue
2. Continusly repeat this process and run in background
3. thread will stop when the application stops
 RunJob -> JudgeRequest
 */