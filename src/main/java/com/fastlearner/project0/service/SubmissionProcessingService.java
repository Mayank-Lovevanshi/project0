package com.fastlearner.project0.service;

import com.fastlearner.project0.entity.Submission;

import java.util.List;

public interface SubmissionProcessingService
{
    public void processSubmission(Long submissionId);
    public void processBatchSubmissions(List<Long>  submissionIds);
}
