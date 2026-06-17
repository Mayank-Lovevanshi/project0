package com.fastlearner.project0.dto.judge0.batch;

import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;

import java.util.List;

public class Judge0BatchSubmissionResponse {

    private List<Judge0SubmissionResponse> submissions;

    public List<Judge0SubmissionResponse> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Judge0SubmissionResponse> submissions) {
        this.submissions = submissions;
    }
}
