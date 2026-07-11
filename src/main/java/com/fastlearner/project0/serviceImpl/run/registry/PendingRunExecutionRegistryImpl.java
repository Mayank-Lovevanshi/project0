package com.fastlearner.project0.serviceImpl.run.registry;
import com.fastlearner.project0.dto.job.RunJob;
import com.fastlearner.project0.service.run.registery.PendingRunExecutionRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
@Component
public class PendingRunExecutionRegistryImpl implements PendingRunExecutionRegistry
{
    private final ConcurrentHashMap<String, RunJob> pendingExecutions = new ConcurrentHashMap<>();

    @Override
    public void put(String token, RunJob job) {
        pendingExecutions.put(token, job);
    }
    @Override
    public RunJob get(String token) {
        return pendingExecutions.get(token);
    }
    @Override
    public void remove(String token) {
        pendingExecutions.remove(token);
    }
}
