package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.CreateProblemRequest;
import com.fastlearner.project0.dto.ProblemDTO;

public interface ProblemService
{
    public ProblemResponse getProblemById(Long id);
    public ProblemResponse createProblem(CreateProblemRequest problemDTO);
    public ProblemResponse deleteProblem(Long id);
}
