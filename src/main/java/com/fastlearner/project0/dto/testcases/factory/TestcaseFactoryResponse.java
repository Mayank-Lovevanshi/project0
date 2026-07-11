package com.fastlearner.project0.dto.testcases.factory;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TestcaseFactoryResponse
{
    private String stdin;
    private Integer numberOfTestCases;
}
