package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.CreateProblemRequest;
import com.fastlearner.project0.dto.ProblemDTO;

public interface ProblemService
{
    public ProblemDTO getProblemById(Long id);
    public ProblemDTO createProblem(CreateProblemRequest problemDTO);
    public ProblemDTO deleteProblem(Long id);
}
