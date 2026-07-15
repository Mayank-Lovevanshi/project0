package com.fastlearner.project0.serviceImpl.util;

import com.fastlearner.project0.entity.Parameter;
import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.StructureRepository;
import com.fastlearner.project0.service.codeGenerator.DatatypeMapping;
import com.fastlearner.project0.service.codeGenerator.parser.UtilityCode;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class DriverCode {
    private final DatatypeMapping datatypeMapping;
    private final StructureRepository structureRepository;
    private final UtilityCode inputParser;
    private final OutputParser outputParser;
    public StringBuilder generateDriverCode(Long problemId)
    {
        Structure structure = structureRepository.findById(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM NOT FOUND"));
        StringBuilder driverCode = new StringBuilder();
        driverCode.append("""
                import java.util.*;
                import java.io.*;
            
                public class Main
                {
                    public static void main(String[] args)
                    {
                        String filePath = "input.txt";
                        Solution solution = new Solution();
                        List<ComparatorResponse> response = new ArrayList<>();""");
                        String returnType = datatypeMapping.javaDatatypeMapping(structure.getReturnType());
                        driverCode.append(returnType).append(" ans;");
                        driverCode.append("""
                                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
                                int testCases = Integer.parseInt(br.readLine());
                                for(int i = 0; i < testCases; i++){""");
                        List<Parameter> parameters = structure.getParameters();
                        int n = parameters.size();
                        for (Parameter parameter : parameters) {
                            driverCode.append(datatypeMapping.javaDatatypeMapping(parameter.getType())).append(" ").append(parameter.getName()).append(" = ");
                            driverCode.append(datatypeMapping.getJavaInputCode(parameter.getType()));
                        }
                        driverCode.append("ans = solution.").append(structure.getMethodName()).append("(");
                        for(int i = 0; i < n; i++){
                            if(i!=n-1) driverCode.append(parameters.get(i).getType()).append(" ").append(parameters.get(i).getName()).append(", ");
                        }
                        driverCode.append(");");
                        driverCode.append(returnType).append(" expectedOutput = ");
                        driverCode.append(datatypeMapping.getJavaInputCode(structure.getReturnType()));
                        driverCode.append("response.add(ComparatorService.compare(ans,expectedOutput));");
                        driverCode.append("""
                                }
                                }catch(IOException e)
                                {
                                   System.out.println(e.getMessage());
                                }
                        System.out.println("{ result : "+response+"}");
                    }
                }""");
                        driverCode.append(inputParser.generateJavaInputUtilityCode());
                        driverCode.append(outputParser.generateJavaOutputUtilityCode());
                        return driverCode;
    }
}
