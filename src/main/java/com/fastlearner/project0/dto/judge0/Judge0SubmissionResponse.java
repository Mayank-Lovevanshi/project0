package com.fastlearner.project0.dto.judge0;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Judge0SubmissionResponse
{
    private String stdout;
    private String stderr;
    private String compile_output;
    private String time;
    private Integer memory;
    private Judge0Status status;
    private String token;
    private String message;
}
