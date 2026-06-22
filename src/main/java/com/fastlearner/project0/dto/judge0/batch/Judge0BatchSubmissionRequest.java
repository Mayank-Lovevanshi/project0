package com.fastlearner.project0.dto.judge0.batch;

import com.fastlearner.project0.dto.judge0.Judge0SubmissionRequest;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Judge0BatchSubmissionRequest
{
    private List<Judge0SubmissionRequest> submissions;
}
