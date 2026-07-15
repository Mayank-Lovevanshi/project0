package com.fastlearner.project0.serviceImpl.codeGenerator.java;

import java.util.Collection;
import java.util.StringJoiner;
import java.util.Arrays;

public class RuntimeData {
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
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\t", "\\t")
                    .replace("\r", "\\r");
            return "\"" + escaped + "\"";
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
                sj.add("\"" + c + "\"");
            }
            return sj.toString();
        }

        // Fallback toString for objects not caught above
        return "\"" + obj.toString() + "\"";
    }
}
