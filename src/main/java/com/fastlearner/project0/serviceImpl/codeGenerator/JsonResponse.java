package com.fastlearner.project0.serviceImpl.codeGenerator;


import com.fastlearner.project0.serviceImpl.codeGenerator.java.RuntimeData;

import java.util.List;
import java.util.StringJoiner;

public class JsonResponse {
    private final boolean isCorrect;
    private final Integer testCaseNumber;
    private final List<RuntimeData> input;
    private final RuntimeData expectedOutput;
    private final RuntimeData actualOutput;

    public JsonResponse(boolean isCorrect, Integer testCaseNumber, List<RuntimeData> input,
                      RuntimeData expectedOutput, RuntimeData actualOutput) {
        this.isCorrect = isCorrect;
        this.testCaseNumber = testCaseNumber;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
    }

    @Override
    public String toString() {
        // 1. Serialize the inputs list
        StringJoiner inputJoiner = new StringJoiner(",", "[", "]");
        for (RuntimeData p : input) {
            inputJoiner.add(String.format(
                    "{\"name\":\"%s\",\"type\":{\"baseType\":\"%s\",\"dimensions\":%d},\"value\":%s}",
                    p.getName(), p.getBaseType(), p.getDimensions(), RuntimeData.serialize(p.getValue())
            ));
        }

        // 2. Serialize the expected output structure
        String expectedJson = String.format(
                "{\"type\":{\"baseType\":\"%s\",\"dimensions\":%d},\"value\":%s}",
                expectedOutput.getBaseType(), expectedOutput.getDimensions(), RuntimeData.serialize(expectedOutput.getValue())
        );

        // 3. Serialize the actual execution output structure
        String actualJson = String.format(
                "{\"type\":{\"baseType\":\"%s\",\"dimensions\":%d},\"value\":%s}",
                actualOutput.getBaseType(), actualOutput.getDimensions(), RuntimeData.serialize(actualOutput.getValue())
        );

        // 4. Combine into a single JSON object string
        return String.format(
                "{\"testCaseNumber\":%d,\"isCorrect\":%b,\"input\":%s,\"expectedOutput\":%s,\"actualOutput\":%s}",
                testCaseNumber, isCorrect, inputJoiner.toString(), expectedJson, actualJson
        );
    }
}