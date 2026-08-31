package com.fastlearner.project0.serviceImpl.submission.worker;

import com.fastlearner.project0.dto.job.SubmissionJob;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.service.submission.execution.SubmissionExecutionService;
import com.fastlearner.project0.service.submission.queue.SubmissionExecutionQueue;
import com.fastlearner.project0.service.submission.registery.PendingSubmissionExecutionRegistry;
import com.fastlearner.project0.service.worker.ExecutionWorker;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SubmissionExecutionWorker implements ExecutionWorker {
    private final SubmissionExecutionQueue submissionExecutionQueue;
    private final PendingSubmissionExecutionRegistry pendingSubmissionExecutionRegistry;
    private final JudgeService judgeService;
    private volatile boolean isRunning;
    @Override
    public void run() {
        isRunning = true;
        while (isRunning || !submissionExecutionQueue.isEmpty()) {
            List<SubmissionJob> jobs = new ArrayList<>();
            while(jobs.size()<20)
            {
                SubmissionJob job = submissionExecutionQueue.poll();
                if(job==null) break;
                jobs.add(job);
            }
            if(jobs.isEmpty()) continue;
            List<JudgeRequest> requests = jobs.stream().map(SubmissionJob::getJudgeRequest).toList();
            Judge0TokenResponse[] responses = judgeService.execute(requests);
            for(int i=0;i<responses.length;i++)
            {
                pendingSubmissionExecutionRegistry.put(responses[i].getToken(),jobs.get(i));
            }
        }
    }
    public void stop() {
        isRunning = false;
    }
}
