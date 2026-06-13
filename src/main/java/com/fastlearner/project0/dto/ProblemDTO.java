package com.fastlearner.project0.dto;

import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.entity.User;
import com.fastlearner.project0.enums.Difficulty;
import com.fastlearner.project0.enums.ProblemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDTO {
    private Long id;
    private String title;
    private String statement;
    private String inputFormat;
    private String outputFormat;
    private String constraints;
    private Difficulty difficulty;
    private Integer timeLimitMs;
    private Integer memoryLimitMb;
    private ProblemStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User createdBy;
    private List<TestCase> testCases;
}

