package com.fastlearner.project0.service.codeGenerator.parser;

import com.fastlearner.project0.enums.Language;

public interface InputParser
{
    public StringBuilder generateJavaInputUtilityCode();
    public StringBuilder generateCppInputUtilityCode();
    public StringBuilder generatePythonInputUtilityCode();
}
