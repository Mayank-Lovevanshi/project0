package com.fastlearner.project0.service.execution.queue;

import com.fastlearner.project0.dto.job.Job;

public interface ExecutionQueue
{
    public void enqueue(Job<?> job);
    public Job<?> take() throws InterruptedException;
    public boolean isEmpty();
}
