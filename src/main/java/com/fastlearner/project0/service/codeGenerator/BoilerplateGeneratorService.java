package com.fastlearner.project0.service.codeGenerator;

import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;

public interface BoilerplateGeneratorService
{
    public String generateJavaDriverCode(Structure structure);
    public String generateJavaStarterCode(Structure structure);
    public String generateCppStarterCode(Structure structure);
    public String generateCppDriverCode(Structure structure);
    public String generatePythonStarterCode(Structure structure);
    public String generatePythonDriverCode(Structure structure);
}
