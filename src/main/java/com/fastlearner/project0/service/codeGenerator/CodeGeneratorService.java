package com.fastlearner.project0.service.codeGenerator;

import com.fastlearner.project0.enums.Language;

public interface CodeGeneratorService
{
    public String generateDriverCode(Long problemId, Language language);
    public String generateStarterCode(Long problemId, Language language);
}
