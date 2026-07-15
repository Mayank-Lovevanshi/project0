package com.fastlearner.project0.serviceImpl.structure;

import com.fastlearner.project0.dto.structure.AdminStructureResponse;
import com.fastlearner.project0.dto.structure.StructureDTO;
import com.fastlearner.project0.dto.structure.StudentStructureResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.exceptions.InvalidArgumentException;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.ProblemRepository;
import com.fastlearner.project0.repository.StructureRepository;
import com.fastlearner.project0.service.structure.StructureService;
import com.fastlearner.project0.service.codeGenerator.CodeGeneratorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StructureServiceImpl implements StructureService
{
    private final ModelMapper modelMapper;
    private final ProblemRepository problemRepository;
    private final StructureRepository structureRepository;
    private final CodeGeneratorService codeGeneratorService;

    @Override
    public StructureDTO saveProblemTemplate(StructureDTO structureDTO, Long problemId) {
        if(problemId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID_ProblemTemplateServiceImpl");
        Problem problem = problemRepository.findById(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND_ProblemTemplateServiceImpl"));
        Structure structure = modelMapper.map(structureDTO,Structure.class);
        structure.setProblem(problem);
        structureRepository.save(structure);
        return structureDTO;
    }

    @Override
    public StructureDTO updateProblemTemplate(StructureDTO structureRequestDTO, Long templateId, Long problemId) {
        Structure existing = structureRepository
                .findById(templateId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("RESOURCE_NOT_FOUND_TEMPLATE_PROBLEM"));

        existing.setMethodName(structureRequestDTO.getMethodName());
        existing.setReturnType(structureRequestDTO.getReturnType());
        existing.setParameters(structureRequestDTO.getParameters());

        Structure saved =
                structureRepository.save(existing);
        return modelMapper.map(saved,StructureDTO.class);
    }

    @Override
    public StructureDTO deleteProblemTemplate(Long structureId, Long problemId) {
        if(problemId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID_ProblemTemplateServiceImpl");
        Structure structure = structureRepository.findById(structureId).orElseThrow(()->new ResourceNotFoundException("STRUCTURE_NOT_FOUND_ProblemTemplateImpl"));
        structureRepository.deleteById(structureId);
        return modelMapper.map(structure,StructureDTO.class);
    }

    @Override
    public AdminStructureResponse getProblemTemplate(Language language, Long problemId) {
        String driverCode = codeGeneratorService.generateDriverCode(problemId,language).toString();
        String starterCode = codeGeneratorService.generateStarterCode(problemId,language).toString();
        AdminStructureResponse adminStructureResponse = new AdminStructureResponse();
        adminStructureResponse.setDriverCode(driverCode);
        adminStructureResponse.setStarterCode(starterCode);
        return adminStructureResponse;
    }
    @Override
    public Structure getStructure(Long problemId)
    {
        return structureRepository.findByProblemId(problemId).orElseThrow(()->new ResourceNotFoundException("STRUCTURE_NOT_FOUND_PROBLEM"));
    }

    @Override
    public StudentStructureResponse getStarterCode(Long problemId,Language language) {
        StringBuilder starterCode = codeGeneratorService.generateStarterCode(problemId,language);
        StudentStructureResponse studentStructureResponse = new StudentStructureResponse();
        studentStructureResponse.setStarterCode(starterCode.toString());
        return studentStructureResponse;
    }
}
