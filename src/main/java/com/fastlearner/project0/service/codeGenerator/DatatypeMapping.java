package com.fastlearner.project0.service.codeGenerator;

import com.fastlearner.project0.enums.Datatype;
import com.fastlearner.project0.enums.Language;

public interface DatatypeMapping
{
    Language getLanguage();
    String mapType(Datatype datatype);
    String getInputCode(Datatype datatype);
}
