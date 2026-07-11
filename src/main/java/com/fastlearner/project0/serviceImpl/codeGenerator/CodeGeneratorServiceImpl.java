package com.fastlearner.project0.serviceImpl.codeGenerator;

import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.codeGenerator.BoilerplateGeneratorService;
import com.fastlearner.project0.service.codeGenerator.CodeGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeGeneratorServiceImpl implements CodeGeneratorService {
    private final BoilerplateGeneratorService boilerplateGeneratorService;
    @Override
    public StringBuilder generateDriverCode(Long problemId, Language language) {
        return switch (language) {
            case JAVA -> boilerplateGeneratorService.generateJavaDriverCode(problemId);
            case CPP -> boilerplateGeneratorService.generateCppDriverCode(problemId);
            case PYTHON -> boilerplateGeneratorService.generatePythonStarterCode(problemId);
        };
    }
    @Override
    public StringBuilder generateStarterCode(Long problemId, Language language) {
        return switch (language) {
            case JAVA -> boilerplateGeneratorService.generateJavaStarterCode(problemId);
            case CPP -> boilerplateGeneratorService.generateCppStarterCode(problemId);
            case PYTHON -> boilerplateGeneratorService.generatePythonDriverCode(problemId);
        };
    }

}
