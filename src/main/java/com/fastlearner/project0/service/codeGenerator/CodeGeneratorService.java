package com.fastlearner.project0.service.codeGenerator;

import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;

public interface CodeGeneratorService
{
    public String generateDriverCode(Structure structure, Language language);
    public String generateStarterCode(Structure structure, Language language);
    public String generateInputUtilityCode(Language language);
    public String generateJavaOutputUtilityCode(Language language);
}
