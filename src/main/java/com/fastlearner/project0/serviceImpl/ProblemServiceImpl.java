package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.CreateProblemRequest;
import com.fastlearner.project0.dto.ProblemResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.User;
import com.fastlearner.project0.enums.ProblemStatus;
import com.fastlearner.project0.exceptions.AuthenticationException;
import com.fastlearner.project0.exceptions.InvalidArgumentException;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.ProblemRepository;
import com.fastlearner.project0.repository.UserRepository;
import com.fastlearner.project0.service.ProblemService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    public ProblemServiceImpl(ProblemRepository problemRepository, ModelMapper modelMapper,UserRepository userRepository)
    {
        this.problemRepository = problemRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }
    public ProblemResponse getProblemById(Long id)
    {
        if(id<=0) throw new InvalidArgumentException("INVALID_ID");
        Problem problem = problemRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND"));
        return modelMapper.map(problem, ProblemResponse.class);
    }
    public ProblemResponse createProblem(CreateProblemRequest requestProblem)
    {
        Problem problem=modelMapper.map(requestProblem,Problem.class);
        //set up user id who set this problem
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails==null) throw new AuthenticationException("NO_ACTIVE_SESSION");
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("USER_NOT_FOUND"));
        problem.setCreatedBy(user);
        problem.setStatus(ProblemStatus.DRAFTED);
        problemRepository.save(problem);
        return modelMapper.map(problem,ProblemResponse.class);
    }
    public ProblemResponse deleteProblem(Long id)
    {
        if(id<=0) throw new InvalidArgumentException("INVALID_ID");
        Problem problem = problemRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("PROBLEM_NOT_FOUND"));
        problemRepository.delete(problem);
        return modelMapper.map(problem,ProblemResponse.class);
    }
}