package com.fastlearner.project0.service.submission.queue;
import com.fastlearner.project0.dto.job.SubmissionJob;

import java.util.concurrent.TimeUnit;

public interface SubmissionExecutionQueue
{
     void enqueue(SubmissionJob job);
     SubmissionJob take() throws InterruptedException;
     boolean isEmpty();
     SubmissionJob poll();
}
