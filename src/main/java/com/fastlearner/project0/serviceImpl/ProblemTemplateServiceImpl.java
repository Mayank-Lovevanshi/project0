package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.problemTemplate.ProblemTemplateDTO;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.ProblemTemplate;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.exceptions.InvalidArgumentException;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.ProblemRepository;
import com.fastlearner.project0.repository.ProblemTemplateRepository;
import com.fastlearner.project0.service.ProblemTemplateService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProblemTemplateServiceImpl implements ProblemTemplateService
{
    private final ProblemTemplateRepository problemTemplateRepository;
    private final ModelMapper modelMapper;
    private final ProblemRepository problemRepository;
    public ProblemTemplateServiceImpl(ProblemTemplateRepository problemTemplateRepository, ModelMapper modelMapper, ProblemRepository problemRepository) {
        this.problemTemplateRepository = problemTemplateRepository;
        this.modelMapper = modelMapper;
        this.problemRepository = problemRepository;
    }
    @Override
    public ProblemTemplateDTO saveProblemTemplate(ProblemTemplateDTO problemTemplateDTO, Long problemId) {
        if(problemId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID_ProblemTemplateServiceImpl");
        Problem problem = problemRepository.findById(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND_ProblemTemplateServiceImpl"));
        ProblemTemplate problemTemplate = modelMapper.map(problemTemplateDTO,ProblemTemplate.class);
        problemTemplate.setProblem(problem);
        problemTemplateRepository.save(problemTemplate);
        return modelMapper.map(problemTemplate,ProblemTemplateDTO.class);
    }

    @Override
    public ProblemTemplateDTO updateProblemTemplate(ProblemTemplateDTO problemTemplateDTO, Long templateId, Long problemId) {
        if(problemId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID_ProblemTemplateServiceImpl");
        if(templateId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_TEMPLATE_ID_ProblemTemplateImpl");
        Problem problem = problemRepository.findById(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND_ProblemTemplateServiceImpl"));
        ProblemTemplate problemTemplate = modelMapper.map(problemTemplateDTO,ProblemTemplate.class);
        problemTemplate.setProblem(problem);
        problemTemplate.setDriverCode(problemTemplate.getDriverCode());
        problemTemplate.setStarterCode(problemTemplate.getStarterCode());
        problemTemplateRepository.save(problemTemplate);
        return modelMapper.map(problemTemplate,ProblemTemplateDTO.class);
    }

    @Override
    public ProblemTemplateDTO deleteProblemTemplate(Long templateId, Long problemId) {
        if(problemId<=0) throw new InvalidArgumentException("INVALID_PROBLEM_ID_ProblemTemplateServiceImpl");
        ProblemTemplate problemTemplate = problemTemplateRepository.findById(templateId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_TEMPLATE_NOT_FOUND_ProblemTemplateImpl"));
        problemTemplateRepository.deleteById(templateId);
        return modelMapper.map(problemTemplate,ProblemTemplateDTO.class);
    }

    @Override
    public ProblemTemplateDTO getProblemTemplate(Language language, Long problemId) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_ID_NOT_FOUND_ProblemTemplateImpl"));
        ProblemTemplate problemTemplate = problemTemplateRepository.findByProblemAndLanguage(problem,language).orElseThrow(()->new ResourceNotFoundException("PROBLEM_TEMPLATE_NOT_FOUND_ProblemTemplateImpl"));
        return modelMapper.map(problemTemplate,ProblemTemplateDTO.class);
    }

    @Override
    public List<ProblemTemplateDTO> getAllProblemTemplates(Long problemId) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND_ProblemTemplateImpl"));
        List<ProblemTemplate> problemTemplateList= problemTemplateRepository.findAllByProblem(problem).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND_ProblemTemplateImpl"));
        List<ProblemTemplateDTO> problemTemplateDTOList = new ArrayList<>();
        for(ProblemTemplate problemTemplate : problemTemplateList){
            problemTemplateDTOList.add(modelMapper.map(problemTemplate,ProblemTemplateDTO.class));
        }
        return problemTemplateDTOList;
    }
}
