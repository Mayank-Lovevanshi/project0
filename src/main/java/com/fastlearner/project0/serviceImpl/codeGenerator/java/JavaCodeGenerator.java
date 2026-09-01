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
                """.formatted(returnType,structure.getMethodName(),parameterList);
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


        // 3. Generate the main runner class
        String driverTemplate =
                """
                        import java.io.*;
                         import java.util.*;
                         public class Main {
                             public static void main(String[] args) {
                                 Scanner reader = new Scanner(System.in);
                                 Solution solution = new Solution();
                                 %s ans;
                                     int testcases = Integer.parseInt(reader.nextLine().trim());
                                     for(int i = 0; i < testcases; i++) {
                         %s
                                         ans = solution.%s(%s);
                                         OutputUtility.print(ans);
                                         System.out.println("---FASTELEARNER_DELIMITER_42---");
                                     }
                             }
                         }
                """.formatted(returnType,
                        parameterDeclaration.toString(),
                        structure.getMethodName(),
                        methodArgs);
        return driverTemplate + "\n" +
                utilityCode.generateInputUtilityCode() + "\n" +
                utilityCode.generateOutputUtilityCode();

    }
}