package com.fastlearner.project0.serviceImpl.util;

import com.fastlearner.project0.entity.Parameter;
import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.util.CodeGeneratorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {
    private final DatatypeMappingServiceImpl datatypeMapping;
    public CodeGeneratorServiceImpl(DatatypeMappingServiceImpl datatypeMapping) {
        this.datatypeMapping = datatypeMapping;
    }
    private String generateCppStarterCode(Structure structure)
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

    private String generateJavaStarterCode(Structure structure)
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

    private String generateJavaDriverCode(Structure structure)
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
        code.append("class Print {\n");
        code.append("\t").append("public static void print(").append("int[] arr) {\n");
        code.append("\t\t").append("for(int x : arr) {\n");
        code.append("\t\t\t").append("System.out.print(x+\" \");\n");
        code.append("\t\t").append("}\n");
        code.append("\t\tSystem.out.println();\n");
        code.append("\t}\n");

        code.append("\t").append("public static void print(").append("long[] arr) {\n");
        code.append("\t\t").append("for(long x : arr) {\n");
        code.append("\t\t\t").append("System.out.print(x+\" \");\n");
        code.append("\t\t").append("}\n");
        code.append("\t\tSystem.out.println();\n");
        code.append("\t}\n");

        code.append("\t").append("public static void print(").append("double[] arr) {\n");
        code.append("\t\t").append("for(double x : arr) {\n");
        code.append("\t\t\t").append("System.out.print(x+\" \");\n");
        code.append("\t\t").append("}\n");
        code.append("\t\tSystem.out.println();\n");
        code.append("\t}\n");

        code.append("\t").append("public static void print(").append("boolean[] arr) {\n");
        code.append("\t\t").append("for(boolean x : arr) {\n");
        code.append("\t\t\t").append("System.out.print(x+\" \");\n");
        code.append("\t\t").append("}\n");
        code.append("\t\tSystem.out.println();\n");
        code.append("\t}\n");

        code.append("\t").append("public static void print(").append("String[] arr) {\n");
        code.append("\t\t").append("for(String x : arr) {\n");
        code.append("\t\t\t").append("System.out.print(x+\" \");\n");
        code.append("\t\t").append("}\n");
        code.append("\t\tSystem.out.println();\n");
        code.append("\t}\n");

        code.append("\t").append("public static <T> void print(").append("T[] arr) {\n");
        code.append("\t\t").append("for(T x : arr) {\n");
        code.append("\t\t\t").append("System.out.print(x+\" \");\n");
        code.append("\t\t").append("}\n");
        code.append("\t\tSystem.out.println();\n");
        code.append("\t}\n");
        code.append("}\n");
        return code.toString();
    }

    @Override
    public String generateDriverCode(Structure structure, Language language) {
        return switch (language) {
            case JAVA -> generateJavaDriverCode(structure);
            case CPP -> generateCppDriverCode(structure);
            case PYTHON -> generatePythonDriverCode(structure);
        };
    }

    private String generatePythonDriverCode(Structure structure) {
        return "";
    }

    private String generateCppDriverCode(Structure structure) {
        return "";
    }


    @Override
    public String generateStarterCode(Structure structure, Language language) {
        return switch (language) {
            case JAVA -> generateJavaStarterCode(structure);
            case CPP -> generateCppStarterCode(structure);
            case PYTHON -> generatePythonStarterCode(structure);
        };
    }

    @Override
    public String generateInputUtilityCode(Language language) {
        return switch (language)
        {
            case JAVA -> generateJavaInputUtilityCode();
            case CPP -> generateCppInputUtilityCode();
            case PYTHON -> generatePythonInputUtilityCode();
        };
    }
    private String generateCppInputUtilityCode() {
        return "";
    }
    private String generatePythonInputUtilityCode() {
        return "";
    }
    private String generateJavaInputUtilityCode()
    {
        return """
                class InputParser
                {
                    public static int[] readIntArray(Scanner input)
                    {
                        int size = input.nextInt();
                        int[] arr = new int[size];
                        for (int i = 0; i < size; i++)
                            arr[i] = input.nextInt();
                        return arr;
                    }
                    public static String[] readStringArray(Scanner input)
                    {
                        int size = input.nextInt();
                        String[] arr = new String[size];
                        for (int i = 0; i < size; i++)
                            arr[i] = input.next();
                        return arr;
                    }
                    public static double[] readDoubleArray(Scanner input)
                    {
                        int size = input.nextInt();
                        double[] arr = new double[size];
                        for (int i = 0; i < size; i++)
                            arr[i] = input.nextDouble();
                        return arr;
                    }
                    public static char[] readCharArray(Scanner input)
                    {
                        int size = input.nextInt();
                        char[] arr = new char[size];
                        for (int i = 0; i < size; i++)
                            arr[i] = input.next().charAt(0);
                        return arr;
                    }
                    public static long[] readLongArray(Scanner input)
                    {
                        int size = input.nextInt();
                        long[] arr = new long[size];
                        for (int i = 0; i < size; i++)
                            arr[i] = input.nextLong();
                        return arr;
                    }
                    public static int[][]  readIntMatrix(Scanner input)
                    {
                        int row = input.nextInt();
                        int col = input.nextInt();
                        int[][] arr = new int[row][col];
                        for (int i = 0; i < row; i++)
                            for (int j = 0; j < col; j++)
                                arr[i][j] = input.nextInt();
                        return arr;
                    }
                    public static String[][] readStringMatrix(Scanner input)
                    {
                        int row = input.nextInt();
                        int col = input.nextInt();
                        String[][] arr = new String[row][col];
                        for (int i = 0; i < row; i++)
                            for (int j = 0; j < col; j++)
                                arr[i][j] = input.next();
                        return arr;
                    }
                    public static double[][] readDoubleMatrix(Scanner input)
                    {
                        int row = input.nextInt();
                        int col = input.nextInt();
                        double[][] arr = new double[row][col];
                        for (int i = 0; i < row; i++)
                            for (int j = 0; j < col; j++)
                                arr[i][j] = input.nextDouble();
                        return arr;
                    }
                    public static char[][] readCharMatrix(Scanner input)
                    {
                        int row = input.nextInt();
                        int col = input.nextInt();
                        char[][] arr = new char[row][col];
                        for(int i = 0; i < row; i++){
                            for(int j = 0; j < col; j++){
                                arr[i][j] = input.next().charAt(0);
                            }
                        }
                        return arr;
                    }
                    public static long[][] readLongMatrix(Scanner input)
                    {
                        int row = input.nextInt();
                        int col = input.nextInt();
                        long[][] arr = new long[row][col];
                        for(int i = 0; i < row; i++){
                            for(int j = 0; j < col; j++){
                                arr[i][j] = input.nextLong();
                            }
                        }
                        return arr;
                    }
                }
                """;
    }
    private String generatePythonStarterCode(Structure structure) {
        return "";
    }
}
