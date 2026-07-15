package com.fastlearner.project0.serviceImpl.codeGenerator.java;
import com.fastlearner.project0.service.codeGenerator.parser.UtilityCode;
import org.springframework.stereotype.Service;

@Service
public class JavaUtilityCode implements UtilityCode
{
    public String generateInputUtilityCode()
    {
        return"""
                class InputParser
                {
                    public static int[] readIntArray(BufferedReader reader)
                    {
                        int size = reader.nextInt();
                        int[] arr = new int[size];
                        for (int i = 0; i < size; i++)
                            arr[i] = reader.nextInt();
                        return arr;
                    }
                    public static String[] readStringArray(BufferedReader reader)
                    {
                        int size = reader.nextInt();
                        String[] arr = new String[size];
                        for (int i = 0; i < size; i++)
                            arr[i] = reader.next();
                        return arr;
                    }
                    public static double[] readDoubleArray(BufferedReader reader)
                    {
                        int size = reader.nextInt();
                        double[] arr = new double[size];
                        for (int i = 0; i < size; i++)
                            arr[i] = reader.nextDouble();
                        return arr;
                    }
                    public static char[] readCharArray(BufferedReader reader)
                    {
                        int size = reader.nextInt();
                        char[] arr = new char[size];
                        for (int i = 0; i < size; i++)
                            arr[i] = reader.next().charAt(0);
                        return arr;
                    }
                    public static long[] readLongArray(BufferedReader reader)
                    {
                        int size = reader.nextInt();
                        long[] arr = new long[size];
                        for (int i = 0; i < size; i++)
                            arr[i] = reader.nextLong();
                        return arr;
                    }
                    public static int[][]  readIntMatrix(BufferedReader reader)
                    {
                        int row = reader.nextInt();
                        int col = reader.nextInt();
                        int[][] arr = new int[row][col];
                        for (int i = 0; i < row; i++)
                            for (int j = 0; j < col; j++)
                                arr[i][j] = reader.nextInt();
                        return arr;
                    }
                    public static String[][] readStringMatrix(BufferedReader reader)
                    {
                        int row = reader.nextInt();
                        int col = reader.nextInt();
                        String[][] arr = new String[row][col];
                        for (int i = 0; i < row; i++)
                            for (int j = 0; j < col; j++)
                                arr[i][j] = reader.next();
                        return arr;
                    }
                    public static double[][] readDoubleMatrix(BufferedReader reader)
                    {
                        int row = reader.nextInt();
                        int col = reader.nextInt();
                        double[][] arr = new double[row][col];
                        for (int i = 0; i < row; i++)
                            for (int j = 0; j < col; j++)
                                arr[i][j] = reader.nextDouble();
                        return arr;
                    }
                    public static char[][] readCharMatrix(BufferedReader reader)
                    {
                        int row = reader.nextInt();
                        int col = reader.nextInt();
                        char[][] arr = new char[row][col];
                        for(int i = 0; i < row; i++){
                            for(int j = 0; j < col; j++){
                                arr[i][j] = reader.next().charAt(0);
                            }
                        }
                        return arr;
                    }
                    public static long[][] readLongMatrix(BufferedReader reader)
                    {
                        int row = reader.nextInt();
                        int col = reader.nextInt();
                        long[][] arr = new long[row][col];
                        for(int i = 0; i < row; i++){
                            for(int j = 0; j < col; j++){
                                arr[i][j] = reader.nextLong();
                            }
                        }
                        return arr;
                    }
                }
                """;
    }
    @Override
    public String generateOutputUtilityCode() {
        return """
                class Print {
               \s
                        public static void print(int[] arr) {
                            printPrimitiveArray(arr);
                        }
               \s
                        public static void print(long[] arr) {
                            printPrimitiveArray(arr);
                        }
               \s
                        public static void print(double[] arr) {
                            printPrimitiveArray(arr);
                        }
               \s
                        public static void print(boolean[] arr) {
                            printPrimitiveArray(arr);
                        }
               \s
                        public static void print(char[] arr) {
                            printPrimitiveArray(arr);
                        }
               \s
                        public static void print(String[] arr) {
                            printObjectArray(arr);
                        }
               \s
                        public static <T> void print(T[] arr) {
                            printObjectArray(arr);
                        }
               \s
                        private static void printObjectArray(Object[] arr) {
                            for (Object x : arr) {
                                System.out.print(x + " ");
                            }
                            System.out.println();
                        }
               \s
                        private static void printPrimitiveArray(int[] arr) {
                            for (int x : arr) {
                                System.out.print(x + " ");
                            }
                            System.out.println();
                        }
               \s
                        private static void printPrimitiveArray(long[] arr) {
                            for (long x : arr) {
                                System.out.print(x + " ");
                            }
                            System.out.println();
                        }
               \s
                        private static void printPrimitiveArray(double[] arr) {
                            for (double x : arr) {
                                System.out.print(x + " ");
                            }
                            System.out.println();
                        }
               \s
                        private static void printPrimitiveArray(boolean[] arr) {
                            for (boolean x : arr) {
                                System.out.print(x + " ");
                            }
                            System.out.println();
                        }
               \s
                        private static void printPrimitiveArray(char[] arr) {
                            for (char x : arr) {
                                System.out.print(x + " ");
                            }
                            System.out.println();
                        }
               \s
                        public static void print(int[][] matrix) {
                            for (int[] row : matrix) {
                                print(row);
                            }
                        }
                       \s
                        public static void print(char[][] matrix){
                            for (char[] row : matrix) {
                                print(row);
                            }
                        }
                       \s
                        public static void print(long[][] matrix) {
                            for (long[] row : matrix) {
                                print(row);
                            }
                        }
               \s
                        public static void print(double[][] matrix) {
                            for (double[] row : matrix) {
                                print(row);
                            }
                        }
               \s
                        public static void print(String[][] matrix) {
                            for (String[] row : matrix) {
                                print(row);
                            }
                        }
                    }
               \s""";
    }

    @Override
    public String generateRuntimeOutputUtilityCode() {
        return """
                class RuntimeOutput {
                    private final String baseType;
                    private final int dimensions;
                    private final Object value;
                
                    public RuntimeOutput(String baseType, int dimensions, Object value) {
                        this.baseType = baseType;
                        this.dimensions = dimensions;
                        this.value = value;
                    }
                
                    public String getBaseType() { return baseType; }
                    public int getDimensions() { return dimensions; }
                    public Object getValue() { return value; }
                }
                """;
    }

    @Override
    public String generateRuntimeParameterUtilityCode() {
        return """
                class RuntimeParameter {
                    private final String name;
                    private final String baseType;
                    private final int dimensions;
                    private final Object value;
                
                    public RuntimeParameter(String name, String baseType, int dimensions, Object value) {
                        this.name = name;
                        this.baseType = baseType;
                        this.dimensions = dimensions;
                        this.value = value;
                    }
                
                    public String getName() { return name; }
                    public String getBaseType() { return baseType; }
                    public int getDimensions() { return dimensions; }
                    public Object getValue() { return value; }
                }
                """;
    }

}
