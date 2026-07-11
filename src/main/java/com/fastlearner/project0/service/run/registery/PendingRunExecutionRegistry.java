package com.fastlearner.project0.service.run.registery;
import com.fastlearner.project0.dto.job.RunJob;

public interface PendingRunExecutionRegistry
{
    public void put(String token, RunJob job);
    public RunJob get(String token);
    public void remove(String token);
}
