package com.fastlearner.project0.dto.submission;

import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.enums.Verdict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionResponse
{
    private Long id;
    private Long problemId;
    private String problemTitle;
    private Language language;
    private Verdict verdict;
    private Long executionTimeMs;
    private Long memoryUsedKb;
    private Integer passedTestCases;
    private Integer totalTestCases;
    private LocalDateTime submittedAt;
    private String errorMessage;
}

