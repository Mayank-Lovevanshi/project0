package com.fastlearner.project0.dto.judge0;

import com.fastlearner.project0.enums.Verdict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeResult
{
    private String output;
    private Long executionTimeMs;
    private Long memoryUsedKb;
    private Verdict verdict;
}
