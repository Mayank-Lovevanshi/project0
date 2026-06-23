package com.fastlearner.project0.dto.judge0;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("compile_output")
    private String compileOutput;
    private String time;
    private Integer memory;
    private Judge0Status status;
    private String token;
    private String message;
}
