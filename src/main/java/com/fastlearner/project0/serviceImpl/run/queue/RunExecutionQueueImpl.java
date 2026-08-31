package com.fastlearner.project0.serviceImpl.run.queue;

import com.fastlearner.project0.dto.job.RunJob;
import com.fastlearner.project0.service.run.queue.RunExecutionQueue;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class RunExecutionQueueImpl implements RunExecutionQueue {
    private final BlockingQueue<RunJob> queue =  new LinkedBlockingQueue<>(1000);
    @Override
    public void enqueue(RunJob job) {
        queue.offer(job);
    }

    @Override
    public RunJob take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public RunJob poll()  {
        return queue.poll();
    }

}
