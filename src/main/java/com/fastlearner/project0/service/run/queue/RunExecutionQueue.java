package com.fastlearner.project0.service.run.queue;

import com.fastlearner.project0.dto.job.RunJob;

import java.util.concurrent.TimeUnit;

public interface RunExecutionQueue
{
     void enqueue(RunJob job);
     RunJob take() throws InterruptedException;
     boolean isEmpty();
     RunJob poll() throws InterruptedException;
}
