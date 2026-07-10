package com.fastlearner.project0.serviceImpl.execution.queue;

import com.fastlearner.project0.dto.job.Job;
import com.fastlearner.project0.service.execution.queue.ExecutionQueue;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
@Component
public class ExecutionQueueImpl implements ExecutionQueue {
    private final BlockingQueue<Job<?>> queue =  new LinkedBlockingQueue<>(1000);
    @Override
    public void enqueue(Job<?> job) {
        queue.offer(job);
    }

    @Override
    public Job<?> take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

}
