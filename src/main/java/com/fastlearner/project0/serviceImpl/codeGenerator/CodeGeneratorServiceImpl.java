package com.fastlearner.project0.serviceImpl.codeGenerator;

import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.codeGenerator.BoilerplateGeneratorService;
import com.fastlearner.project0.service.codeGenerator.parser.InputParser;
import com.fastlearner.project0.service.codeGenerator.parser.OutputParser;
import com.fastlearner.project0.service.codeGenerator.CodeGeneratorService;
import org.springframework.stereotype.Service;

@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {
    private final InputParser inputParser;
    private final OutputParser outputParser;
    private final BoilerplateGeneratorService boilerplateGeneratorService;
    public CodeGeneratorServiceImpl(InputParser inputParser, OutputParser outputParser, BoilerplateGeneratorService boilerplateGeneratorService) {
        this.boilerplateGeneratorService = boilerplateGeneratorService;
        this.inputParser = inputParser;
        this.outputParser = outputParser;
    }
    @Override
    public String generateDriverCode(Structure structure, Language language) {
        return switch (language) {
            case JAVA -> boilerplateGeneratorService.generateJavaDriverCode(structure);
            case CPP -> boilerplateGeneratorService.generateCppDriverCode(structure);
            case PYTHON -> boilerplateGeneratorService.generatePythonStarterCode(structure);
        };
    }
    @Override
    public String generateStarterCode(Structure structure, Language language) {
        return switch (language) {
            case JAVA -> boilerplateGeneratorService.generateJavaStarterCode(structure);
            case CPP -> boilerplateGeneratorService.generateCppStarterCode(structure);
            case PYTHON -> boilerplateGeneratorService.generatePythonDriverCode(structure);
        };
    }

    @Override
    public String generateInputUtilityCode(Language language) {
        return switch (language)
        {
            case JAVA -> inputParser.generateJavaInputUtilityCode();
            case CPP -> inputParser.generateCppInputUtilityCode();
            case PYTHON -> inputParser.generatePythonInputUtilityCode();
        };
    }

    @Override
    public String generateJavaOutputUtilityCode(Language language) {
        return switch (language)
        {
            case JAVA -> outputParser.generateJavaOutputUtilityCode();
            case CPP -> outputParser.generateCppOutputUtilityCode();
            case PYTHON -> outputParser.generatePythonOutputUtilityCode();
        };
    }

}
