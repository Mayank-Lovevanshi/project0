package com.fastlearner.project0.service.codeGenerator;

import com.fastlearner.project0.enums.Datatype;
import com.fastlearner.project0.enums.Language;

public interface DatatypeMappingOrchestrator
{
    String mapType(Datatype datatype, Language language);
    Datatype parseToDatatype(String type, Language language);
    String getInputCode(Datatype datatype, Language language);
}
