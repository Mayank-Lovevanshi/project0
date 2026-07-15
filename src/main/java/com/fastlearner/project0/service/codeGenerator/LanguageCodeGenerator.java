package com.fastlearner.project0.service.codeGenerator;

import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;

public interface LanguageCodeGenerator
{
    Language getLanguage();
    String generateStarterCode(Structure structure);
    String generateDriverCode(Structure structure);
}
