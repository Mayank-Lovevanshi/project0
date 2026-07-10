package com.fastlearner.project0.dto.testcases;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class CreateTestCaseBatchRequest
{
    List<CreateTestCaseRequest> testCases;
}
