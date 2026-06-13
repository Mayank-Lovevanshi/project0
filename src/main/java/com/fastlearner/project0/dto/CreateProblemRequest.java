package com.fastlearner.project0.dto;

import com.fastlearner.project0.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProblemRequest
{
    private String title;
    private String statement;
    private String inputFormat;
    private String outputFormat;
    private String constraints;
    private Difficulty difficulty;
    private Integer timeLimitMs;
    private Integer memoryLimitMb;
}
