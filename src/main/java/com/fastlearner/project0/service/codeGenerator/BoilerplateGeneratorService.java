package com.fastlearner.project0.service.codeGenerator;

import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;

public interface BoilerplateGeneratorService
{
    public StringBuilder generateJavaDriverCode(Structure structure);
    public StringBuilder generateJavaStarterCode(Structure structure);
    public StringBuilder generateCppStarterCode(Structure structure);
    public StringBuilder generateCppDriverCode(Structure structure);
    public StringBuilder generatePythonStarterCode(Structure structure);
    public StringBuilder generatePythonDriverCode(Structure structure);
}
