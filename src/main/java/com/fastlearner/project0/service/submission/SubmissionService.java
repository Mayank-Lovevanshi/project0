package com.fastlearner.project0.service.submission;

import com.fastlearner.project0.dto.submission.CreateSubmissionRequest;
import com.fastlearner.project0.dto.submission.SubmissionResponse;
import com.fastlearner.project0.entity.Submission;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SubmissionService
{
     CompletableFuture<SubmissionResponse> submit(CreateSubmissionRequest createSubmissionRequest);
     SubmissionResponse getSubmissionById(Long id);
     List<SubmissionResponse> getSubmissionsByProblemId(Long problemId);
     List<SubmissionResponse> getMySubmissions();
     Submission findSubmissionById(Long id);
     Submission saveSubmission(Submission submission);
}
