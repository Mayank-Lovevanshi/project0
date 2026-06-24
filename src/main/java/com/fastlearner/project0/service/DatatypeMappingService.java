package com.fastlearner.project0.service;

import com.fastlearner.project0.entity.Parameter;
import com.fastlearner.project0.enums.Datatype;

public interface DatatypeMappingService
{
    public String cppDatatypeMapping(Datatype datatype);
    public String javaDatatypeMapping(Datatype datatype);
    public String getJavaInputCode(Datatype datatype);
}
