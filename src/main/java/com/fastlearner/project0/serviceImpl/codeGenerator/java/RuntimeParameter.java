package com.fastlearner.project0.serviceImpl.codeGenerator.java;

public class RuntimeParameter {
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
