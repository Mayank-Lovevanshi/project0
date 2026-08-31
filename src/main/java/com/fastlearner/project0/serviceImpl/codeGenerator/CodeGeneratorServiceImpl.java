package com.fastlearner.project0.serviceImpl.codeGenerator;

import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.StructureRepository;
import com.fastlearner.project0.service.codeGenerator.CodeGeneratorService;
import com.fastlearner.project0.service.codeGenerator.LanguageCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {
    private final StructureRepository structureRepository;
    private final Map<Language, LanguageCodeGenerator> generatorMap;
    public CodeGeneratorServiceImpl(StructureRepository structureRepository, List<LanguageCodeGenerator> generatorList) {
        this.structureRepository = structureRepository;
        this.generatorMap = generatorList.stream().collect(Collectors.toMap(LanguageCodeGenerator::getLanguage, generator -> generator));
    }
    private Structure getStructure(Long problemId)
    {
        return structureRepository.findByProblemId(problemId).orElseThrow(()->new ResourceNotFoundException("Structure not found"));
    }
    @Override
    public String generateDriverCode(Long problemId, Language language) {
        Structure structure = getStructure(problemId);
        return getGenerator(language).generateDriverCode(structure);
    }
    @Override
    public String generateStarterCode(Long problemId, Language language) {
        Structure structure = getStructure(problemId);
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
