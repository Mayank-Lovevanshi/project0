package com.fastlearner.project0.serviceImpl;
import com.fastlearner.project0.enums.Datatype;
import com.fastlearner.project0.exceptions.DatatypeNotSupportedException;
import com.fastlearner.project0.service.DatatypeMappingService;
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
            case BOOLEAN -> "boolean";
            case DOUBLE -> "double";
            case LONG -> "long";
            case CHARACTER ->"char";
        });
       for(int i=0;i< parameter.getDimension();i++)
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
       for(int i=0;i< datatype.getDimension();i++)
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
                case LONG -> "intput.nextLong()";
                case BOOLEAN -> "input.nextBoolean()";
                case DOUBLE -> "intput.nextDouble()";
                case STRING -> "input.nextString()";
                case CHARACTER -> "input.nextChar()";
            };
        }
        else if(datatype.getDimension()==1)
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
        throw new DatatypeNotSupportedException("Dimension > 1 not supported");
    }
}
