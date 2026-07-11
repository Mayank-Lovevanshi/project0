package com.fastlearner.project0.service.codeGenerator;

public interface BoilerplateGeneratorService
{
    public StringBuilder generateJavaDriverCode(Long problemId);
    public StringBuilder generateJavaStarterCode(Long problemId);
    public StringBuilder generateCppStarterCode(Long problemId);
    public StringBuilder generateCppDriverCode(Long problemId);
    public StringBuilder generatePythonStarterCode(Long problemId);
    public StringBuilder generatePythonDriverCode(Long problemId);
}
