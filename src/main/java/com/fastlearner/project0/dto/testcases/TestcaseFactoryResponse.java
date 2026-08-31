package com.fastlearner.project0.dto.testcases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestcaseFactoryResponse
{
    private int size;
    private String expectedOutput;
}
