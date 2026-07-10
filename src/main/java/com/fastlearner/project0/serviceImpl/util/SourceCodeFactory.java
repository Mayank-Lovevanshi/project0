package com.fastlearner.project0.serviceImpl.util;

import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.StructureRepository;
import com.fastlearner.project0.service.codeGenerator.CodeGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SourceCodeFactory
{
    private final CodeGeneratorService codeGeneratorService;
    private final StructureRepository structureRepository;
    public String getSourceCode(Long problemId,Language language,String userCode)
    {
        Structure structure = structureRepository.findByProblemId(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND_SourceCode"));
        StringBuilder driverCode = codeGeneratorService.generateDriverCode(structure, language);
        StringBuilder inputParserCode = codeGeneratorService.generateInputUtilityCode(language);
        StringBuilder outputParserCode = codeGeneratorService.generateJavaOutputUtilityCode(language);
        return driverCode.append("\n").append(userCode).append("\n")
                .append(inputParserCode).append("\n").append(outputParserCode).toString();
    }
}
