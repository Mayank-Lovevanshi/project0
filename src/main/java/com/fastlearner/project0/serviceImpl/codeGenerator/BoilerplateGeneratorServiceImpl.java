package com.fastlearner.project0.serviceImpl.codeGenerator;

import com.fastlearner.project0.entity.Parameter;
import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.StructureRepository;
import com.fastlearner.project0.service.codeGenerator.BoilerplateGeneratorService;
import com.fastlearner.project0.service.codeGenerator.DatatypeMappingService;
import com.fastlearner.project0.service.codeGenerator.parser.InputParser;
import com.fastlearner.project0.service.codeGenerator.parser.OutputParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoilerplateGeneratorServiceImpl implements BoilerplateGeneratorService {
    private final DatatypeMappingService datatypeMapping;
    private final StructureRepository structureRepository;
    private final InputParser inputParser;
    private final OutputParser outputParser;

    public StringBuilder generateCppStarterCode(Long problemId)
    {
        Structure structure = structureRepository.findByProblemId(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_TEMPLATE_NOT_FOUND_BoilerplateGeneratorServiceImpl"));
        StringBuilder code = new StringBuilder();
        code.append("class Solution {\n");
        code.append("public:\n");
        code.append("\t").append(datatypeMapping.cppDatatypeMapping(structure.getReturnType())).append(" ").append(structure.getMethodName());
        code.append("(");
        List<Parameter> parameters = structure.getParameters();
        int n = parameters.size();
        for(int i = 0; i < n; i++)
        {
            code.append(datatypeMapping.cppDatatypeMapping(parameters.get(i).getType())).append(" ").append(parameters.get(i).getName());
            if(i<n-1) code.append(", ");
        }
        code.append(") {\n");
        code.append("\n");
        code.append("\t}");
        code.append("\n};");
        return code;
    }

    public StringBuilder generateJavaStarterCode(Long problemId)
    {
        Structure structure = structureRepository.findByProblemId(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_TEMPLATE_NOT_FOUND_BoilerplateGeneratorServiceImpl"));
        StringBuilder code = new StringBuilder();
        code.append("class Solution {\n");
        code.append("\t").append("public").append(" ").append(datatypeMapping.javaDatatypeMapping(structure.getReturnType())).append(" ").append(structure.getMethodName());
        code.append("(");
        List<Parameter> parameters = structure.getParameters();
        int n = parameters.size();
        for(int i = 0; i < n; i++)
        {
            code.append(datatypeMapping.javaDatatypeMapping(parameters.get(i).getType())).append(" ").append(parameters.get(i).getName());
            if(i<n-1) code.append(", ");
        }
        code.append(") {\n");
        code.append("\n");
        code.append("\t}");
        code.append("\n}");
        return code;
    }

    public StringBuilder generateJavaDriverCode(Long  problemId)
    {
        Structure structure = structureRepository.findByProblemId(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_TEMPLATE_NOT_FOUND_BoilerplateGeneratorServiceImpl"));
        StringBuilder code = new StringBuilder();
        int n = structure.getParameters().size();
        code.append("import java.util.*;\n");
        code.append("public class Main {\n");
        code.append("\t").append("public static void main(String[] args) {\n");
        code.append("\t\t").append("Solution solution = new Solution();\n");
        code.append("\t\t").append("Scanner input = new Scanner(System.in);\n");
        code.append("\t\t").append("int test = input.nextInt();\n");
        code.append("\t\t").append("for(int i = 0; i < test; i++) {\n");
        for(Parameter parameter : structure.getParameters()) {
            code.append("\t\t\t").append(datatypeMapping.javaDatatypeMapping(parameter.getType())).append(" ").append(parameter.getName()).append(" = ").append(datatypeMapping.getJavaInputCode(parameter.getType())).append(";\n");
        }
        code.append("\t\t\t").append(datatypeMapping.javaDatatypeMapping(structure.getReturnType())).append(" ").append("ans = ").append("solution.").append(structure.getMethodName()).append("(");
        List<Parameter> parameters = structure.getParameters();
        for(int i = 0; i < n; i++)
        {
            code.append(parameters.get(i).getName());
            if(i!=n-1) code.append(", ");
        }
        code.append(");\n");
        code.append("\t\t\t");
        if(structure.getReturnType().isPrimitive()) code.append("System.out.println(ans);\n");
        else code.append("Print.print(ans);\n");
        code.append("\t\t}\n");
        code.append("\t}\n");
        code.append("}\n\n");
        code.append(inputParser.generateJavaInputUtilityCode());
        code.append("\n\n");
        code.append(outputParser.generateJavaOutputUtilityCode());
        return code;
    }

    @Override
    public StringBuilder generateCppDriverCode(Long problemId) {
        return new  StringBuilder();
    }

    @Override
    public StringBuilder generatePythonStarterCode(Long problemId) {
        return new  StringBuilder();
    }

    @Override
    public StringBuilder generatePythonDriverCode(Long problemId) {
        return new  StringBuilder();
    }
}
