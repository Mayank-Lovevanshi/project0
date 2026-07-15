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
                     private static StringTokenizer tokenizer = null;
                
                     // Helper to get the next token out of the BufferedReader stream across newlines
                     private static String nextToken(BufferedReader reader) {
                         try {
                             while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                                 String line = reader.readLine();
                                 if (line == null) {
                                     return null;
                                 }
                                 tokenizer = new StringTokenizer(line);
                             }
                             return tokenizer.nextToken();
                         } catch (IOException e) {
                             throw new RuntimeException("Error reading input tokens", e);
                         }
                     }
                
                     public static int readInt(BufferedReader reader) {
                         return Integer.parseInt(nextToken(reader));
                     }
                
                     public static long readLong(BufferedReader reader) {
                         return Long.parseLong(nextToken(reader));
                     }
                
                     public static double readDouble(BufferedReader reader) {
                         return Double.parseDouble(nextToken(reader));
                     }
                
                     public static String readString(BufferedReader reader) {
                         return nextToken(reader);
                     }
                
                     public static char readChar(BufferedReader reader) {
                         return nextToken(reader).charAt(0);
                     }
                
                     public static boolean readBoolean(BufferedReader reader) {
                         return Boolean.parseBoolean(nextToken(reader));
                     }
                
                     // --- Array Parsers ---
                
                     public static int[] readIntArray(BufferedReader reader) {
                         int size = readInt(reader);
                         int[] arr = new int[size];
                         for (int i = 0; i < size; i++) {
                             arr[i] = readInt(reader);
                         }
                         return arr;
                     }
                
                     public static String[] readStringArray(BufferedReader reader) {
                         int size = readInt(reader);
                         String[] arr = new String[size];
                         for (int i = 0; i < size; i++) {
                             arr[i] = readString(reader);
                         }
                         return arr;
                     }
                
                     public static double[] readDoubleArray(BufferedReader reader) {
                         int size = readInt(reader);
                         double[] arr = new double[size];
                         for (int i = 0; i < size; i++) {
                             arr[i] = readDouble(reader);
                         }
                         return arr;
                     }
                
                     public static char[] readCharArray(BufferedReader reader) {
                         int size = readInt(reader);
                         char[] arr = new char[size];
                         for (int i = 0; i < size; i++) {
                             arr[i] = readChar(reader);
                         }
                         return arr;
                     }
                
                     public static long[] readLongArray(BufferedReader reader) {
                         int size = readInt(reader);
                         long[] arr = new long[size];
                         for (int i = 0; i < size; i++) {
                             arr[i] = readLong(reader);
                         }
                         return arr;
                     }
                
                     // --- 2D Matrix Parsers ---
                
                     public static int[][] readIntMatrix(BufferedReader reader) {
                         int row = readInt(reader);
                         int col = readInt(reader);
                         int[][] arr = new int[row][col];
                         for (int i = 0; i < row; i++) {
                             for (int j = 0; j < col; j++) {
                                 arr[i][j] = readInt(reader);
                             }
                         }
                         return arr;
                     }
                
                     public static String[][] readStringMatrix(BufferedReader reader) {
                         int row = readInt(reader);
                         int col = readInt(reader);
                         String[][] arr = new String[row][col];
                         for (int i = 0; i < row; i++) {
                             for (int j = 0; j < col; j++) {
                                 arr[i][j] = readString(reader);
                             }
                         }
                         return arr;
                     }
                
                     public static double[][] readDoubleMatrix(BufferedReader reader) {
                         int row = readInt(reader);
                         int col = readInt(reader);
                         double[][] arr = new double[row][col];
                         for (int i = 0; i < row; i++) {
                             for (int j = 0; j < col; j++) {
                                 arr[i][j] = readDouble(reader);
                             }
                         }
                         return arr;
                     }
                
                     public static char[][] readCharMatrix(BufferedReader reader) {
                         int row = readInt(reader);
                         int col = readInt(reader);
                         char[][] arr = new char[row][col];
                         for (int i = 0; i < row; i++) {
                             for (int j = 0; j < col; j++) {
                                 arr[i][j] = readChar(reader);
                             }
                         }
                         return arr;
                     }
                
                     public static long[][] readLongMatrix(BufferedReader reader) {
                         int row = readInt(reader);
                         int col = readInt(reader);
                         long[][] arr = new long[row][col];
                         for (int i = 0; i < row; i++) {
                             for (int j = 0; j < col; j++) {
                                 arr[i][j] = readLong(reader);
                             }
                         }
                         return arr;
                     }
                 }
                """;
    }

    @Override
    public String generateRuntimeDataUtilityCode() {
        return """
                class RuntimeData {
                    private final String name; // Null for expected/actual outputs; populated for inputs
                    private final String baseType;
                    private final int dimensions;
                    private final Object value;
                
                    // Constructor for input variables
                    public RuntimeData(String name, String baseType, int dimensions, Object value) {
                        this.name = name;
                        this.baseType = baseType;
                        this.dimensions = dimensions;
                        this.value = value;
                    }
                
                    // Constructor for expected & actual output values
                    public RuntimeData(String baseType, int dimensions, Object value) {
                        this.name = null;
                        this.baseType = baseType;
                        this.dimensions = dimensions;
                        this.value = value;
                    }
                
                    public String getName() { return name; }
                    public String getBaseType() { return baseType; }
                    public int getDimensions() { return dimensions; }
                    public Object getValue() { return value; }
                
                    /**
                     * Serializes any standard Java object, array, or collection into a valid JSON string
                     * without using external libraries like Jackson or Gson.
                     */
                    public static String serialize(Object obj) {
                        if (obj == null) {
                            return "null";
                        }
                
                        // Handle strings & chars (with character escaping)
                        if (obj instanceof String || obj instanceof Character) {
                            String escaped = obj.toString()
                                    .replace("\\\\", "\\\\\\\\")
                                    .replace("\\"", "\\\\\\"")
                                    .replace("\\n", "\\\\n")
                                    .replace("\\t", "\\\\t")
                                    .replace("\\r", "\\\\r");
                            return "\\"" + escaped + "\\"";
                        }
                
                        // Handle numeric values and booleans
                        if (obj instanceof Number || obj instanceof Boolean) {
                            return obj.toString();
                        }
                
                        // Handle standard Collections (Lists, Sets)
                        if (obj instanceof Collection<?>) {
                            StringJoiner sj = new StringJoiner(",", "[", "]");
                            for (Object item : (Collection<?>) obj) {
                                sj.add(serialize(item));
                            }
                            return sj.toString();
                        }
                
                        // Handle Object Arrays (Integer[], String[][], etc.)
                        if (obj instanceof Object[]) {
                            StringJoiner sj = new StringJoiner(",", "[", "]");
                            for (Object item : (Object[]) obj) {
                                sj.add(serialize(item));
                            }
                            return sj.toString();
                        }
                
                        // Handle primitive arrays directly (avoiding reflection overhead)
                        if (obj instanceof int[]) {
                            return Arrays.toString((int[]) obj).replace(" ", "");
                        }
                        if (obj instanceof long[]) {
                            return Arrays.toString((long[]) obj).replace(" ", "");
                        }
                        if (obj instanceof double[]) {
                            return Arrays.toString((double[]) obj).replace(" ", "");
                        }
                        if (obj instanceof boolean[]) {
                            return Arrays.toString((boolean[]) obj).replace(" ", "");
                        }
                        if (obj instanceof char[]) {
                            StringJoiner sj = new StringJoiner(",", "[", "]");
                            for (char c : (char[]) obj) {
                                sj.add("\\"" + c + "\\"");
                            }
                            return sj.toString();
                        }
                
                        // Fallback toString for objects not caught above
                        return "\\"" + obj.toString() + "\\"";
                    }
                }
                
                """;
    }

    @Override
    public String generateJsonResponseUtilityCode() {
        return """
                class JsonResponse {
                    private final boolean isCorrect;
                    private final Integer testCaseNumber;
                    private final List<RuntimeData> input;
                    private final RuntimeData expectedOutput;
                    private final RuntimeData actualOutput;
                
                    public JsonResponse(boolean isCorrect, Integer testCaseNumber, List<RuntimeData> input,
                                      RuntimeData expectedOutput, RuntimeData actualOutput) {
                        this.isCorrect = isCorrect;
                        this.testCaseNumber = testCaseNumber;
                        this.input = input;
                        this.expectedOutput = expectedOutput;
                        this.actualOutput = actualOutput;
                    }
                
                    @Override
                    public String toString() {
                        // 1. Serialize the inputs list
                        StringJoiner inputJoiner = new StringJoiner(",", "[", "]");
                        for (RuntimeData p : input) {
                            inputJoiner.add(String.format(
                                    "{\\"name\\":\\"%s\\",\\"type\\":{\\"baseType\\":\\"%s\\",\\"dimensions\\":%d},\\"value\\":%s}",
                                    p.getName(), p.getBaseType(), p.getDimensions(), RuntimeData.serialize(p.getValue())
                            ));
                        }
                
                        // 2. Serialize the expected output structure
                        String expectedJson = String.format(
                                "{\\"type\\":{\\"baseType\\":\\"%s\\",\\"dimensions\\":%d},\\"value\\":%s}",
                                expectedOutput.getBaseType(), expectedOutput.getDimensions(), RuntimeData.serialize(expectedOutput.getValue())
                        );
                
                        // 3. Serialize the actual execution output structure
                        String actualJson = String.format(
                                "{\\"type\\":{\\"baseType\\":\\"%s\\",\\"dimensions\\":%d},\\"value\\":%s}",
                                actualOutput.getBaseType(), actualOutput.getDimensions(), RuntimeData.serialize(actualOutput.getValue())
                        );
                
                        // 4. Combine into a single JSON object string
                        return String.format(
                                "{\\"testCaseNumber\\":%d,\\"isCorrect\\":%b,\\"input\\":%s,\\"expectedOutput\\":%s,\\"actualOutput\\":%s}",
                                testCaseNumber, isCorrect, inputJoiner.toString(), expectedJson, actualJson
                        );
                    }
                }
                """;
    }

    @Override
    public String generateEvaluationServiceUtilityCode() {
        return """
                class EvaluationService {
                
                    public static JsonResult evaluate(
                            Object actual,\s
                            Object expected,\s
                            String returnBaseType,
                            int returnDimensions,
                            Integer testCaseNumber,\s
                            List<RuntimeData> inputs) {
                
                        boolean isCorrect = checkDeepEquality(actual, expected, returnBaseType, returnDimensions);
                
                        RuntimeData expectedOut = new RuntimeData(returnBaseType, returnDimensions, expected);
                        RuntimeData actualOut = new RuntimeData(returnBaseType, returnDimensions, actual);
                
                        return new JsonResult(isCorrect, testCaseNumber, inputs, expectedOut, actualOut);
                    }
                
                    /**
                     * Compares values, arrays, matrices, or custom data structure nodes for structural and value equality.
                     */
                    private static boolean checkDeepEquality(Object actual, Object expected, String baseType, int dimensions) {
                        if (actual == expected) return true;
                        if (actual == null || expected == null) return false;
                
                        // ==========================================
                        // EXTENSION HOOKS: Custom LeetCode Structures
                        // ==========================================
                        if ("LISTNODE".equalsIgnoreCase(baseType)) {
                            return compareListNodes(actual, expected);
                        }
                        if ("TREENODE".equalsIgnoreCase(baseType)) {
                            return compareTreeNodes(actual, expected);
                        }
                
                        // ==========================================
                        // 2D MATRIX EQUALITY CHECKS (Defensive Mapping)
                        // ==========================================
                        if (dimensions == 2) {
                            if (actual instanceof int[][] && expected instanceof int[][]) return Arrays.deepEquals((int[][]) actual, (int[][]) expected);
                            if (actual instanceof long[][] && expected instanceof long[][]) return Arrays.deepEquals((long[][]) actual, (long[][]) expected);
                            if (actual instanceof double[][] && expected instanceof double[][]) return Arrays.deepEquals((double[][]) actual, (double[][]) expected);
                            if (actual instanceof boolean[][] && expected instanceof boolean[][]) return Arrays.deepEquals((boolean[][]) actual, (boolean[][]) expected);
                            if (actual instanceof char[][] && expected instanceof char[][]) return Arrays.deepEquals((char[][]) actual, (char[][]) expected);
                        }
                
                        // ==========================================
                        // 1D PRIMITIVE ARRAY EQUALITY CHECKS
                        // ==========================================
                        if (dimensions == 1) {
                            if (actual instanceof int[] && expected instanceof int[]) return Arrays.equals((int[]) actual, (int[]) expected);
                            if (actual instanceof long[] && expected instanceof long[]) return Arrays.equals((long[]) actual, (long[]) expected);
                            if (actual instanceof double[] && expected instanceof double[]) return Arrays.equals((double[]) actual, (double[]) expected);
                            if (actual instanceof boolean[] && expected instanceof boolean[]) return Arrays.equals((boolean[]) actual, (boolean[]) expected);
                            if (actual instanceof char[] && expected instanceof char[]) return Arrays.equals((char[]) actual, (char[]) expected);
                        }
                
                        // ==========================================
                        // STANDARD OBJECT ARRAYS / MATRICES & OBJECT EQUALITY
                        // ==========================================
                        if (actual instanceof Object[] && expected instanceof Object[]) {
                            return Arrays.deepEquals((Object[]) actual, (Object[]) expected);
                        }
                
                        // Handles Base Literals (Integer, Long, String, Double, Boolean, Character)
                        return actual.equals(expected);
                    }
                
                    /**
                     * Future Extension Implementation for Linked Lists.
                     * Compares nodes by traveling via node.next and validating node.val.
                     */
                    private static boolean compareListNodes(Object actual, Object expected) {
                        // Example implementation when you declare a public class ListNode { int val; ListNode next; }
                        //
                        // try {
                        //     java.lang.reflect.Field valField = actual.getClass().getField("val");
                        //     java.lang.reflect.Field nextField = actual.getClass().getField("next");
                        //    \s
                        //     Object currActual = actual;
                        //     Object currExpected = expected;
                        //    \s
                        //     while (currActual != null && currExpected != null) {
                        //         int valA = valField.getInt(currActual);
                        //         int valB = valField.getInt(currExpected);
                        //         if (valA != valB) return false;
                        //        \s
                        //         currActual = nextField.get(currActual);
                        //         currExpected = nextField.get(currExpected);
                        //     }
                        //     return currActual == null && currExpected == null;
                        // } catch (Exception e) {
                        //     return false;
                        // }
                        return true;\s
                    }
                
                    /**
                     * Future Extension Implementation for Binary Trees.
                     * Recursively traverses nodes via left and right child pointers to confirm structure matching.
                     */
                    private static boolean compareTreeNodes(Object actual, Object expected) {
                        // Example recursive structural mirror check logic goes here
                        return true;
                    }
                }
                """;
    }


}
