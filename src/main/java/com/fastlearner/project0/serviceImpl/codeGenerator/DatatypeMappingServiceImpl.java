package com.fastlearner.project0.serviceImpl.codeGenerator;
import com.fastlearner.project0.enums.Datatype;
import com.fastlearner.project0.exceptions.DatatypeNotSupportedException;
import com.fastlearner.project0.service.codeGenerator.DatatypeMappingService;
import org.springframework.stereotype.Component;

@Component
public class DatatypeMappingServiceImpl implements DatatypeMappingService
{
    @Override
    public String cppDatatypeMapping(Datatype parameter)
    {
        StringBuilder type = new StringBuilder(switch (parameter.getBaseType()) {
            case STRING -> "string";
            case INTEGER -> "int";
            case BOOLEAN -> "bool";
            case DOUBLE -> "double";
            case LONG -> "long long";
            case CHARACTER ->"char";
        });
       for(int i=0;i< parameter.getDimensions();i++)
       {
           type = new StringBuilder("vector<" + type + ">");
       }
       return type.toString();
    }
    @Override
    public String javaDatatypeMapping(Datatype datatype)
    {
       StringBuilder type = new StringBuilder(switch (datatype.getBaseType()) {
           case STRING -> "String";
           case INTEGER -> "int";
           case BOOLEAN -> "boolean";
           case DOUBLE -> "double";
           case LONG -> "long";
           case CHARACTER ->"char";
       });
       for(int i=0;i< datatype.getDimensions();i++)
       {
           type.append("[]");
       }
       return type.toString();
    }
    @Override
    public String getJavaInputCode(Datatype datatype)
    {
        if(datatype.isPrimitive())
        {
            return switch (datatype.getBaseType()) {
                case INTEGER -> "input.nextInt()";
                case LONG -> "input.nextLong()";
                case BOOLEAN -> "input.nextBoolean()";
                case DOUBLE -> "input.nextDouble()";
                case STRING -> "input.next()";
                case CHARACTER -> "input.next().charAt(0)";
            };
        }
        else if(datatype.getDimensions()==1)
        {
            return switch(datatype.getBaseType())
            {
                case INTEGER -> "InputParser.readIntArray(input)";
                case LONG -> "InputParser.readLongArray(input)";
                case BOOLEAN -> "InputParser.readBooleanArray(input)";
                case DOUBLE -> "InputParser.readDoubleArray(input)";
                case STRING -> "InputParser.readStringArray(input)";
                case CHARACTER -> "InputParser.readCharArray(input)";
            };
        }
        else if(datatype.getDimensions()==2)
        {
            return switch(datatype.getBaseType()) {
                case INTEGER -> "InputParser.readIntMatrix(input)";
                case LONG -> "InputParser.readLongMatrix(input)";
                case BOOLEAN -> "InputParser.readBooleanMatrix(input)";
                case DOUBLE -> "InputParser.readDoubleMatrix(input)";
                case STRING -> "InputParser.readStringMatrix(input)";
                case CHARACTER -> "InputParser.readCharMatrix(input)";
            };
        }
        throw new DatatypeNotSupportedException("Dimension > 2 not supported");
    }
}
