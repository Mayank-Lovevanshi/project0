package com.fastlearner.project0.service;

import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;

public interface CodeGeneratorService
{
    public String generateCppStarterCode(Structure structure);
    public String generateJavaStarterCode(Structure structure);
    public String generateJavaDriverCode(Structure structure);
}
