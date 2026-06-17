package com.fastlearner.project0.dto.judge0.batch;

import com.fastlearner.project0.dto.judge0.Judge0SubmissionRequest;

import java.util.List;

public class Judge0BatchSubmissionRequest {

    private List<Judge0SubmissionRequest> submissions;

    public List<Judge0SubmissionRequest> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Judge0SubmissionRequest> submissions) {
        this.submissions = submissions;
    }
}
