package com.fastlearner.project0.service.run.queue;

import com.fastlearner.project0.dto.job.RunJob;

public interface RunExecutionQueue
{
    public void enqueue(RunJob job);
    public RunJob take() throws InterruptedException;
    public boolean isEmpty();
}
