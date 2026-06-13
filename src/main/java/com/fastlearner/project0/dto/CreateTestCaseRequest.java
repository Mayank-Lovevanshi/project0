package com.fastlearner.project0.dto;

import com.fastlearner.project0.enums.TestCaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestCaseRequest
{
    private String inputData;
    private String expectedOutput;
    private TestCaseType testCaseType;
    private Integer sequenceNumber;
}
