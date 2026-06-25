package com.fastlearner.project0.service.submission;

import com.fastlearner.project0.dto.submission.CreateSubmissionRequest;
import com.fastlearner.project0.dto.submission.SubmissionResponse;

import java.util.List;

public interface SubmissionService
{
    public SubmissionResponse submit(CreateSubmissionRequest createSubmissionRequest);
    public SubmissionResponse getSubmissionById(Long id);
    public List<SubmissionResponse> getSubmissionsByProblemId(Long problemId);
    public List<SubmissionResponse> getMySubmissions();

}
