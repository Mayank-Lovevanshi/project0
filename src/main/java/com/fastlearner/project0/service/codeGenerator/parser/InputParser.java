package com.fastlearner.project0.service.codeGenerator.parser;

import com.fastlearner.project0.enums.Language;

public interface InputParser
{
    public String generateJavaInputUtilityCode();
    public String generateCppInputUtilityCode();
    public String generatePythonInputUtilityCode();
}
