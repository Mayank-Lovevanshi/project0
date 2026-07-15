package com.fastlearner.project0.serviceImpl.codeGenerator;

import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.codeGenerator.CodeGeneratorService;
import com.fastlearner.project0.service.codeGenerator.LanguageCodeGenerator;
import com.fastlearner.project0.service.structure.StructureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {
    private final StructureService structureService;
    private final Map<Language, LanguageCodeGenerator> generatorMap;
    public CodeGeneratorServiceImpl(StructureService structureService, List<LanguageCodeGenerator> generatorList) {
        this.structureService = structureService;
        this.generatorMap = generatorList.stream().collect(Collectors.toMap(LanguageCodeGenerator::getLanguage, generator -> generator));
    }
    @Override
    public String generateDriverCode(Long problemId, Language language) {
        Structure structure = structureService.getStructure(problemId);
        return getGenerator(language).generateDriverCode(structure);
    }
    @Override
    public String generateStarterCode(Long problemId, Language language) {
        Structure structure = structureService.getStructure(problemId);
        return getGenerator(language).generateStarterCode(structure);
    }
    private LanguageCodeGenerator getGenerator(Language language) {
        LanguageCodeGenerator generator = generatorMap.get(language);
        if(generator == null) {
            throw new UnsupportedOperationException("LANGUAGE_CODE_GENERATOR_UNSUPPORTED");
        }
        return generator;
    }
}
