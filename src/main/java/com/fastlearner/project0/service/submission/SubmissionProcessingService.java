package com.fastlearner.project0.service.submission;

import java.util.List;

public interface SubmissionProcessingService
{
    public void processBatchSubmissions(List<Long>  submissionIds);
}
