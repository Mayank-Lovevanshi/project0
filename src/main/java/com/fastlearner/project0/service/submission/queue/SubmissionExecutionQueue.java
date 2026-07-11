package com.fastlearner.project0.service.submission.queue;

import com.fastlearner.project0.dto.job.SubmissionJob;

public interface SubmissionExecutionQueue
{
    public void enqueue(SubmissionJob job);
    public SubmissionJob take() throws InterruptedException;
    public boolean isEmpty();
}
