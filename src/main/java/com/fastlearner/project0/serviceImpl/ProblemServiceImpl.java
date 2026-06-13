package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.CreateProblemRequest;
import com.fastlearner.project0.dto.ProblemDTO;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.repository.ProblemRepository;
import com.fastlearner.project0.service.ProblemService;
import org.modelmapper.ModelMapper;

import java.util.Optional;

public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper;
    public ProblemServiceImpl(ProblemRepository problemRepository,ModelMapper modelMapper)
    {
        this.problemRepository = problemRepository;
        this.modelMapper = modelMapper;
    }

    public ProblemDTO getProblemById(Long id)
    {
        if(id<=0) throw new RuntimeException("INVALID_ID");
        Optional<Problem> optionalProblem = problemRepository.findById(id);
        //return optionalProblem.map(problem -> modelMapper.map(problem, ProblemDTO.class)).orElse(null);
        if(optionalProblem.isEmpty()) return null;
        return modelMapper.map(optionalProblem.get(),ProblemDTO.class);
    }
    public ProblemDTO createProblem(CreateProblemRequest requestProblem)
    {
        Problem problem=modelMapper.map(requestProblem,Problem.class);
        //set up user id who set this problem
        problemRepository.save(problem);
        ProblemDTO problemDTO = modelMapper.map(problem,ProblemDTO.class);
        return problemDTO;
    }
    public ProblemDTO deleteProblem(Long id)
    {
        Problem problem = problemRepository.findById(id).orElse(null);
        if(problem==null) return null;
        problemRepository.delete(problem);
        ProblemDTO problemDTO = modelMapper.map(problem,ProblemDTO.class);
        return problemDTO;
    }
}
