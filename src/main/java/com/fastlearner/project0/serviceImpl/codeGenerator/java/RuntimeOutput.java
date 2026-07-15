package com.fastlearner.project0.serviceImpl.codeGenerator.java;

public class RuntimeOutput {
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
