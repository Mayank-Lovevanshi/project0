package com.fastlearner.project0.service.submission.registery;
import com.fastlearner.project0.dto.job.SubmissionJob;

public interface PendingSubmissionExecutionRegistry
{
    public void put(String token, SubmissionJob job);
    public SubmissionJob get(String token);
    public void remove(String token);
}
