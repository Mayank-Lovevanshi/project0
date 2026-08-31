package com.fastlearner.project0.serviceImpl.run.worker;

import com.fastlearner.project0.dto.job.RunJob;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.service.run.execution.RunExecutionService;
import com.fastlearner.project0.service.run.queue.RunExecutionQueue;
import com.fastlearner.project0.service.run.registery.PendingRunExecutionRegistry;
import com.fastlearner.project0.service.worker.ExecutionWorker;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RunExecutionWorker implements ExecutionWorker
{
    private final JudgeService judgeService;
    private final RunExecutionQueue runExecutionQueue;
    private final PendingRunExecutionRegistry pendingRunExecutionRegistry;
    private volatile boolean isRunning;
    @Override
    public void run() {
        isRunning = true;
        while(!Thread.currentThread().isInterrupted())
        {
            List<RunJob> jobs = new ArrayList<>();
            while(!runExecutionQueue.isEmpty() && jobs.size()<20)
            {
                try {
                    RunJob job = runExecutionQueue.poll();
                    if(job == null) break;
                    jobs.add(job);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
            if(jobs.isEmpty()) continue;
            List<JudgeRequest> requestList = jobs
                    .stream()
                    .map(RunJob::getJudgeRequest).toList();
            Judge0TokenResponse[] responses = judgeService.execute(requestList);
            for(int i=0;i<responses.length;i++)
            {
                pendingRunExecutionRegistry.put(responses[i].getToken(),jobs.get(i));
            }
        }
    }
}
/*
Responsibility of worker thread
1. pick the jobs to run from the queue
2. Continusly repeat this process and run in background
3. thread will stop when the application stops
 RunJob -> JudgeRequest
 */