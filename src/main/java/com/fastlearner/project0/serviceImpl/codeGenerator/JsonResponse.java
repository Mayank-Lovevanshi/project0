package com.fastlearner.project0.serviceImpl.codeGenerator;

import com.fastlearner.project0.serviceImpl.codeGenerator.java.RuntimeOutput;
import com.fastlearner.project0.serviceImpl.codeGenerator.java.RuntimeParameter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class JsonResponse {
    private boolean isCorrect;
    private Integer testCaseNumber;
    private List<RuntimeParameter> input;
    private RuntimeOutput expectedOutput;
    private RuntimeOutput actualOutput;

    public JsonResponse(boolean isCorrect, Integer testCaseNumber, List<RuntimeParameter> input,
                        RuntimeOutput expectedOutput, RuntimeOutput actualOutput) {
        this.isCorrect = isCorrect;
        this.testCaseNumber = testCaseNumber;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
    }

    @Override
    public String toString() {
        // 1. Serialize the "input" array list parameters
        java.util.StringJoiner inputJoiner = new java.util.StringJoiner(",", "[", "]");
        for (RuntimeParameter p : input) {
            inputJoiner.add(String.format(
                    "{\"name\":\"%s\",\"type\":{\"baseType\":\"%s\",\"dimensions\":%d},\"value\":%s}",
                    p.getName(), p.getBaseType(), p.getDimensions(), JsonSerializer.serialize(p.getValue())
            ));
        }

        // 2. Serialize "expectedOutput" to your target block format
        String expectedJson = String.format(
                "{\"type\":{\"baseType\":\"%s\",\"dimensions\":%d},\"value\":%s}",
                expectedOutput.getBaseType(), expectedOutput.getDimensions(), JsonSerializer.serialize(expectedOutput.getValue())
        );

        // 3. Serialize "actualOutput" to your target block format
        String actualJson = String.format(
                "{\"type\":{\"baseType\":\"%s\",\"dimensions\":%d},\"value\":%s}",
                actualOutput.getBaseType(), actualOutput.getDimensions(), JsonSerializer.serialize(actualOutput.getValue())
        );

        // 4. Combine into the master JSON frame
        return String.format(
                "{\"testCaseNumber\":%d,\"isCorrect\":%b,\"input\":%s,\"expectedOutput\":%s,\"actualOutput\":%s}",
                testCaseNumber, isCorrect, inputJoiner.toString(), expectedJson, actualJson
        );
    }
}
