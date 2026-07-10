package com.fastlearner.project0.serviceImpl.execution.registry;

import com.fastlearner.project0.dto.job.Job;
import com.fastlearner.project0.service.execution.registery.PendingExecutionRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
@Component
public class PendingExecutionRegistryImpl implements PendingExecutionRegistry
{
    private final ConcurrentHashMap<String, Job<?>> pendingExecutions = new ConcurrentHashMap<>();

    @Override
    public void put(String token, Job<?> job) {
        pendingExecutions.put(token, job);
    }
    @Override
    public Job<?> get(String token) {
        return pendingExecutions.get(token);
    }
    @Override
    public void remove(String token) {
        pendingExecutions.remove(token);
    }
}
