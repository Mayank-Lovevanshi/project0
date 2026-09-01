
package com.fastlearner.project0.config;

import com.fastlearner.project0.serviceImpl.run.worker.RunExecutionWorker;
import com.fastlearner.project0.serviceImpl.submission.worker.SubmissionExecutionWorker;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AsyncConfig {
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final SubmissionExecutionWorker submissionExecutionWorker;
    private final RunExecutionWorker runExecutionWorker;
    @PostConstruct
    public void start()
    {
        executorService.submit(submissionExecutionWorker);
        executorService.submit(runExecutionWorker);
    }
    @PreDestroy
    public void stop()
    {
        submissionExecutionWorker.stop();
        runExecutionWorker.stop();
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
