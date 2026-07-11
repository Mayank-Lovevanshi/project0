package com.fastlearner.project0.serviceImpl.submission.registry;
import com.fastlearner.project0.dto.job.SubmissionJob;
import com.fastlearner.project0.service.submission.registery.PendingSubmissionExecutionRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
@Component
public class PendingSubmissionExecutionRegistryImpl implements PendingSubmissionExecutionRegistry
{
    private final ConcurrentHashMap<String, SubmissionJob> pendingExecutions = new ConcurrentHashMap<>();

    @Override
    public void put(String token, SubmissionJob job) {
        pendingExecutions.put(token, job);
    }
    @Override
    public SubmissionJob get(String token) {
        return pendingExecutions.get(token);
    }
    @Override
    public void remove(String token) {
        pendingExecutions.remove(token);
    }
}
