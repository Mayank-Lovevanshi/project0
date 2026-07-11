package com.fastlearner.project0.service.codeGenerator;

import com.fastlearner.project0.enums.Language;

public interface CodeGeneratorService
{
    public StringBuilder generateDriverCode(Long problemId, Language language);
    public StringBuilder generateStarterCode(Long problemId, Language language);
}
