package com.fastlearner.project0.dto.judge0.batch;

import com.fastlearner.project0.dto.judge0.JudgeRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Judge0BatchSubmissionRequest
{
    private List<JudgeRequest> submissions;
}
