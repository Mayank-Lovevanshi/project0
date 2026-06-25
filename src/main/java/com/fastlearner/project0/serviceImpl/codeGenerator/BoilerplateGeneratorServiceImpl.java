package com.fastlearner.project0.serviceImpl.codeGenerator;

import com.fastlearner.project0.entity.Parameter;
import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.service.codeGenerator.BoilerplateGeneratorService;
import com.fastlearner.project0.service.codeGenerator.DatatypeMappingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoilerplateGeneratorServiceImpl implements BoilerplateGeneratorService {
    private final DatatypeMappingService datatypeMapping;

    public BoilerplateGeneratorServiceImpl(DatatypeMappingService datatypeMapping) {
        this.datatypeMapping = datatypeMapping;
    }

    public String generateCppStarterCode(Structure structure)
    {
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
        return code.toString();
    }

    public String generateJavaStarterCode(Structure structure)
    {
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
        return code.toString();
    }

    public String generateJavaDriverCode(Structure structure)
    {
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
        code.append("}\n");
        return code.toString();
    }

    @Override
    public String generateCppDriverCode(Structure structure) {
        return "";
    }

    @Override
    public String generatePythonStarterCode(Structure structure) {
        return "";
    }

    @Override
    public String generatePythonDriverCode(Structure structure) {
        return "";
    }
}
