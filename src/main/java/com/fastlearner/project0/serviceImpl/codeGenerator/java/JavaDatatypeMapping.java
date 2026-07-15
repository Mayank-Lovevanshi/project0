package com.fastlearner.project0.serviceImpl.codeGenerator.java;

import com.fastlearner.project0.enums.BaseType;
import com.fastlearner.project0.enums.Datatype;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.exceptions.DatatypeNotSupportedException;
import com.fastlearner.project0.service.codeGenerator.DatatypeMapping;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
@Component
public class JavaDatatypeMapping implements DatatypeMapping
{
    private static final Map<BaseType,String[]> INPUT_CODE_LOOKUP = new EnumMap<>(BaseType.class);
    static {
        INPUT_CODE_LOOKUP.put(BaseType.INTEGER,   new String[]{"Integer.parseInt(reader.readLine().trim())", "InputParser.readIntArray(reader)",     "InputParser.readIntMatrix(reader)"});
        INPUT_CODE_LOOKUP.put(BaseType.LONG,      new String[]{"Long.parseLong(reader.readLine().trim())",    "InputParser.readLongArray(reader)",    "InputParser.readLongMatrix(reader)"});
        INPUT_CODE_LOOKUP.put(BaseType.BOOLEAN,   new String[]{"Boolean.parseBoolean(reader.readLine().trim())","InputParser.readBooleanArray(reader)", "InputParser.readBooleanMatrix(reader)"});
        INPUT_CODE_LOOKUP.put(BaseType.DOUBLE,    new String[]{"Double.parseDouble(reader.readLine().trim())", "InputParser.readDoubleArray(reader)",  "InputParser.readDoubleMatrix(reader)"});
        INPUT_CODE_LOOKUP.put(BaseType.STRING,    new String[]{"reader.readLine()",                          "InputParser.readStringArray(reader)",  "InputParser.readStringMatrix(reader)"});
        INPUT_CODE_LOOKUP.put(BaseType.CHARACTER, new String[]{"reader.readLine().charAt(0)",                 "InputParser.readCharArray(reader)",    "InputParser.readCharMatrix(reader)"});
    }
    @Override
    public Language getLanguage() {
        return Language.JAVA;
    }

    @Override
    public String mapType(Datatype datatype) {
        if(datatype == null || datatype.getBaseType() == null){
            throw new IllegalArgumentException("Datatype or baseType cannot be null");
        }
        StringBuilder typeStr = new StringBuilder(switch (datatype.getBaseType()) {
            case STRING    -> "String";
            case INTEGER   -> "int";
            case BOOLEAN   -> "boolean";
            case DOUBLE    -> "double";
            case LONG      -> "long";
            case CHARACTER -> "char";
        });
        typeStr.append("[]".repeat(Math.max(0, datatype.getDimensions())));
        return typeStr.toString();
    }

    @Override
    public String getInputCode(Datatype datatype) {
        if (datatype == null || datatype.getBaseType() == null) {
            throw new IllegalArgumentException("Datatype or base type cannot be null");
        }
        int dims = datatype.getDimensions();
        if (dims < 0 || dims > 2) {
            throw new DatatypeNotSupportedException("Dimension " + dims + " not supported");
        }
        String[] mappings = INPUT_CODE_LOOKUP.get(datatype.getBaseType());
        if (mappings == null) {
            throw new DatatypeNotSupportedException("Unsupported base type: " + datatype.getBaseType());
        }
        return mappings[dims];
    }
}
