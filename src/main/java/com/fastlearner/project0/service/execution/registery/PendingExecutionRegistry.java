package com.fastlearner.project0.service.execution.registery;

import com.fastlearner.project0.dto.job.Job;

public interface PendingExecutionRegistry
{
    public void put(String token, Job<?> job);
    public Job<?> get(String token);
    public void remove(String token);
}
