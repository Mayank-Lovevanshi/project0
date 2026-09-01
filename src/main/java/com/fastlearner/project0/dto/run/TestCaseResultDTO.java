package com.fastlearner.project0.dto.run;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseResultDTO {
    private Long testCaseId;
    private Integer sequenceNumber;
    private String input;
    private String expectedOutput;
    private String actualOutput;
    private Boolean passed;
    private String errorMessage;
}