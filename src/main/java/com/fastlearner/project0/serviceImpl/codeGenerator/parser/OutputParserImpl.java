package com.fastlearner.project0.serviceImpl.codeGenerator.parser;

import com.fastlearner.project0.service.codeGenerator.parser.OutputParser;
import org.springframework.stereotype.Service;

@Service
public class OutputParserImpl implements OutputParser
{
    @Override
    public String generateJavaOutputUtilityCode() {
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
    public String generateCppOutputUtilityCode() {
        return "";
    }

    @Override
    public String generatePythonOutputUtilityCode() {
        return "";
    }
}
