package com.fastlearner.project0.serviceImpl.codeGenerator.java;
import com.fastlearner.project0.service.codeGenerator.parser.UtilityCode;
import org.springframework.stereotype.Service;

@Service
public class JavaUtilityCode implements UtilityCode
{
    public String generateInputUtilityCode()
    {
        return"""
                  class InputParser {
                      // --- Primitive Parsers ---
                
                      public static int readInt(Scanner reader) {
                          int val = reader.nextInt();
                          return val;
                      }
                
                      public static long readLong(Scanner reader) {
                          long val = reader.nextLong();
                          return val;
                      }
                
                      public static double readDouble(Scanner reader) {
                          double val = reader.nextDouble();
                          return val;
                      }
                
                      public static String readString(Scanner reader) {
                          String val = reader.next();
                          return val;
                      }
                
                      public static char readChar(Scanner reader) {
                          char val = reader.next().charAt(0);
                          return val;
                      }
                
                      public static boolean readBoolean(Scanner reader) {
                          boolean val = reader.nextBoolean();
                          return val;
                      }
                
                      // --- Array Parsers ---
                
                      public static int[] readIntArray(Scanner reader) {
                          int size = reader.nextInt();
                          int[] arr = new int[size];
                          for (int i = 0; i < size; i++) {
                              arr[i] = reader.nextInt();
                          }
                          return arr;
                      }
                
                      public static String[] readStringArray(Scanner reader) {
                          int size = reader.nextInt();
                          String[] arr = new String[size];
                          for (int i = 0; i < size; i++) {
                              arr[i] = reader.next();
                          }
                          return arr;
                      }
                
                      public static double[] readDoubleArray(Scanner reader) {
                          int size = reader.nextInt();
                          double[] arr = new double[size];
                          for (int i = 0; i < size; i++) {
                              arr[i] = reader.nextDouble();
                          }
                          return arr;
                      }
                
                      public static char[] readCharArray(Scanner reader) {
                          int size = reader.nextInt();
                          char[] arr = new char[size];
                          for (int i = 0; i < size; i++) {
                              arr[i] = reader.next().charAt(0);
                          }
                          return arr;
                      }
                
                      public static long[] readLongArray(Scanner reader) {
                          int size = reader.nextInt();
                          long[] arr = new long[size];
                          for (int i = 0; i < size; i++) {
                              arr[i] = reader.nextLong();
                          }
                          return arr;
                      }
                
                      // --- 2D Matrix Parsers ---
                
                      public static int[][] readIntMatrix(Scanner reader) {
                          int row = reader.nextInt();
                          int col = reader.nextInt();
                          int[][] arr = new int[row][col];
                          for (int i = 0; i < row; i++) {
                              for (int j = 0; j < col; j++) {
                                  arr[i][j] = reader.nextInt();
                              }
                          }
                          return arr;
                      }
                
                      public static String[][] readStringMatrix(Scanner reader) {
                          int row = reader.nextInt();
                          int col = reader.nextInt();
                          String[][] arr = new String[row][col];
                          for (int i = 0; i < row; i++) {
                              for (int j = 0; j < col; j++) {
                                  arr[i][j] = reader.next();
                              }
                          }
                          return arr;
                      }
                
                      public static double[][] readDoubleMatrix(Scanner reader) {
                          int row = reader.nextInt();
                          int col = reader.nextInt();
                          double[][] arr = new double[row][col];
                          for (int i = 0; i < row; i++) {
                              for (int j = 0; j < col; j++) {
                                  arr[i][j] = reader.nextDouble();
                              }
                          }
                          return arr;
                      }
                
                      public static char[][] readCharMatrix(Scanner reader) {
                          int row = reader.nextInt();
                          int col = reader.nextInt();
                          char[][] arr = new char[row][col];
                          for (int i = 0; i < row; i++) {
                              for (int j = 0; j < col; j++) {
                                  arr[i][j] = reader.next().charAt(0);
                              }
                          }
                          return arr;
                      }
                
                      public static long[][] readLongMatrix(Scanner reader) {
                          int row = reader.nextInt();
                          int col = reader.nextInt();
                          long[][] arr = new long[row][col];
                          for (int i = 0; i < row; i++) {
                              for (int j = 0; j < col; j++) {
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
                class OutputUtility {
                    public static void print(int ans) {
                        System.out.println(ans);
                    }
                    public static void print(double ans) {
                        System.out.println(ans);
                    }
                    public static void print(char ans) {
                        System.out.println(ans);
                    }
                    public static void print(String ans) {
                        System.out.println(ans);
                    }
                    public static void print(long ans) {
                        System.out.println(ans);
                    }
                    public static void print(int[] ans) {
                        for(int i : ans) {
                            print(i);
                        }
                    }
                    public static void print(double[] ans) {
                        for(double i : ans) {
                            print(i);
                        }
                    }
                    public static void print(char[] ans) {
                        for(char i : ans) {
                            print(i);
                        }
                    }
                    public static void print(String[] ans) {
                        for(String i : ans) {
                            print(i);
                        }
                    }
                    public static void print(long[] ans) {
                        for(long i : ans) {
                            print(i);
                        }
                    }
                    public static void print(int[][] ans) {
                        for(int[] i : ans) {
                            print(i);
                        }
                    }
                    public static void print(double[][] ans) {
                        for(double[] i : ans) {
                            print(i);
                        }
                    }
                    public static void print(char[][] ans) {
                        for(char[] i : ans) {
                            print(i);
                        }
                    }
                    public static void print(String[][] ans) {
                        for(String[] i : ans) {
                            print(i);
                        }
                    }
                    public static void print(long[][] ans) {
                        for(long[] i : ans) {
                            print(i);
                        }
                    }
                }
                """;
    }


}
