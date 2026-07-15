package com.fastlearner.project0.serviceImpl.codeGenerator.java;

import com.fastlearner.project0.entity.Parameter;
import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.codeGenerator.DatatypeMappingOrchestrator;
import com.fastlearner.project0.service.codeGenerator.LanguageCodeGenerator;
import com.fastlearner.project0.service.codeGenerator.parser.UtilityCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JavaCodeGenerator implements LanguageCodeGenerator {
    private final DatatypeMappingOrchestrator datatypeMappingOrchestrator;
    private final UtilityCode utilityCode;
    @Override
    public Language getLanguage() {
        return Language.JAVA;
    }

    @Override
    public String generateStarterCode(Structure structure)
    {
        String returnType = datatypeMappingOrchestrator.mapType(structure.getReturnType(),Language.JAVA);
        String parameterList = structure.getParameters().stream()
                                    .map(parameter -> datatypeMappingOrchestrator.mapType(parameter.getType(),Language.JAVA)+" "+parameter.getName())
                .collect(Collectors.joining(", "));
        return """
                class Solution {
                    public %s %s(%s){
                
                    }
                }
                """.formatted(returnType,structure.getReturnType(),parameterList);
    }

    @Override
    public String generateDriverCode(Structure structure) {
        String returnType = datatypeMappingOrchestrator.mapType(structure.getReturnType(), Language.JAVA);
        String expectedOutputCode = datatypeMappingOrchestrator.getInputCode(structure.getReturnType(), Language.JAVA);

        // 1. Generate local variable declarations to read inputs from the text file
        StringBuilder parameterDeclaration = new StringBuilder();
        for (Parameter parameter : structure.getParameters()) {
            String typeStr = datatypeMappingOrchestrator.mapType(parameter.getType(), Language.JAVA);
            String inputCode = datatypeMappingOrchestrator.getInputCode(parameter.getType(), Language.JAVA);
            parameterDeclaration.append("\t\t\t\t")
                    .append(typeStr).append(" ")
                    .append(parameter.getName()).append(" = ")
                    .append(inputCode).append(";\n");
        }

        // 2. Generate comma-separated arguments for the solution method call
        String methodArgs = structure.getParameters().stream()
                .map(Parameter::getName)
                .collect(Collectors.joining(", "));

        // 3. Create the list of RuntimeData elements matching our unified design
        String parameterObjectsList = structure.getParameters().stream()
                .map(param -> String.format("new RuntimeData(\"%s\", \"%s\", %d, %s)",
                        param.getName(),
                        param.getType().getBaseType().name(),
                        param.getType().getDimensions(),
                        param.getName()))
                .collect(Collectors.joining(", "));

        // 4. Generate the main runner class, using RuntimeData instantiations
        String driverTemplate =
                """
                import java.io.*;
                import java.util.*;
                
                public class Main {
                    public static void main(String[] args) {
                        String filePath = "input.txt";
                        Solution solution = new Solution();
                        int testCaseNumber;
                        List<JsonResult> response = new ArrayList<>();
                        %s ans;
                        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            int testcases = Integer.parseInt(reader.readLine());
                            for(int i = 0; i < testcases; i++) {
                                int testcaseNumber = Integer.parseInt(reader.readLine());
                %s
                                ans = solution.%s(%s);
                                %s expectedOutput = %s;
                                List<RuntimeData> inputList = Arrays.asList(%s);
                                response.add(EvaluationService.evaluate(ans, expectedOutput, "%s", %d, testcaseNumber, inputList));
                            }
                        }
                        catch(IOException e) {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("{ \\"result\\" : " + response + "}");
                    }
                }
                """.formatted(returnType,
                        parameterDeclaration.toString(),
                        structure.getMethodName(),
                        methodArgs,
                        returnType,
                        expectedOutputCode,
                        parameterObjectsList,
                        structure.getReturnType().getBaseType().name(),
                        structure.getReturnType().getDimensions());

        return driverTemplate + "\n" +
                utilityCode.generateInputUtilityCode() +
                "\n" +
                utilityCode.generateRuntimeDataUtilityCode() +
                "\n" +
                utilityCode.generateJsonResponseUtilityCode() +
                "\n" +
                utilityCode.generateEvaluationServiceUtilityCode();
    }
}