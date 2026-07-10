package com.fastlearner.project0.dto.run;

import com.fastlearner.project0.dto.error.ErrorDetails;
import com.fastlearner.project0.enums.Verdict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunResponseDTO
{
    private Verdict verdict;
    private String output;
    private String expectedOutput;
    private String input;
    private Double executionTimeMs;
    private Integer memoryUsedKb;
    private ErrorDetails errorDetails;
}
