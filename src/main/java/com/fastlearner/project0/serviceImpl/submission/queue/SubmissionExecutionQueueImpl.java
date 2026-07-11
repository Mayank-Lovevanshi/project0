package com.fastlearner.project0.serviceImpl.submission.queue;

import com.fastlearner.project0.dto.job.SubmissionJob;
import com.fastlearner.project0.service.submission.queue.SubmissionExecutionQueue;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
@Component
public class SubmissionExecutionQueueImpl implements SubmissionExecutionQueue {
    private final BlockingQueue<SubmissionJob> queue =  new LinkedBlockingQueue<>(1000);
    @Override
    public void enqueue(SubmissionJob job) {
        queue.offer(job);
    }
    @Override
    public SubmissionJob take() throws InterruptedException {
        return queue.take();
    }
    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
