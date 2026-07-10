package com.fastlearner.project0.service.codeGenerator;

import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;

public interface CodeGeneratorService
{
    public StringBuilder generateDriverCode(Structure structure, Language language);
    public StringBuilder generateStarterCode(Structure structure, Language language);
    public StringBuilder generateInputUtilityCode(Language language);
    public StringBuilder generateJavaOutputUtilityCode(Language language);
}
