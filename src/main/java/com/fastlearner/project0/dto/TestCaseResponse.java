package com.fastlearner.project0.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseResponse
{
    private Long id;
    private String inputData;
    private String expectedOutput;
    private Integer sequenceNumber;
}
