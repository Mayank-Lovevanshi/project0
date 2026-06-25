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
import com.fastlearner.project0.service.util.CodeGeneratorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StructureServiceImpl implements StructureService
{
    private final ModelMapper modelMapper;
    private final ProblemRepository problemRepository;
    private final StructureRepository structureRepository;
    private final CodeGeneratorService codeGeneratorService;
    public StructureServiceImpl(ModelMapper modelMapper, ProblemRepository problemRepository, StructureRepository structureRepository, CodeGeneratorService codeGeneratorService) {
        this.modelMapper = modelMapper;
        this.problemRepository = problemRepository;
        this.structureRepository = structureRepository;
        this.codeGeneratorService = codeGeneratorService;
    }
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
        if(problemId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID_ProblemTemplateServiceImpl");
        if(templateId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_TEMPLATE_ID_ProblemTemplateImpl");
        Problem problem = problemRepository.findById(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND_ProblemTemplateServiceImpl"));
        Structure structure = modelMapper.map(structureRequestDTO,Structure.class);
        structure.setProblem(problem);
        structure.setMethodName(structureRequestDTO.getMethodName());
        structure.setReturnType(structureRequestDTO.getReturnType());
        structure.setParameters(structureRequestDTO.getParameters());
        Structure saved = structureRepository.save(structure);
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
        Problem problem = problemRepository.findById(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_ID_NOT_FOUND_ProblemTemplateImpl"));
        Structure structure = structureRepository.findByProblem(problem).orElseThrow(()->new ResourceNotFoundException("PROBLEM_TEMPLATE_NOT_FOUND_ProblemTemplateImpl"));
        String driverCode = codeGeneratorService.generateDriverCode(structure,language);
        String starterCode = codeGeneratorService.generateStarterCode(structure,language);
        AdminStructureResponse adminStructureResponse = new AdminStructureResponse();
        adminStructureResponse.setDriverCode(driverCode);
        adminStructureResponse.setStarterCode(starterCode);
        return adminStructureResponse;
    }

    @Override
    public StudentStructureResponse getStarterCode(Long problemId,Language language) {
        if(problemId<=0) throw new InvalidArgumentException("PROBLEM_ID_ProblemTemplateServiceImpl");
        Problem problem = problemRepository.findById(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND_ProblemTemplateImpl"));
        Structure structure = structureRepository.findByProblem(problem).orElseThrow(()->new ResourceNotFoundException("STRUCTURE_NOT_FOUND_ProblemTemplateImpl"));
        String starterCode = codeGeneratorService.generateStarterCode(structure,language);
        StudentStructureResponse studentStructureResponse = new StudentStructureResponse();
        studentStructureResponse.setStarterCode(starterCode);
        return studentStructureResponse;
    }
}
