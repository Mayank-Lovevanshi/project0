package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.problem.CreateProblemRequest;
import com.fastlearner.project0.dto.problem.ProblemResponse;
import com.fastlearner.project0.dto.problem.UpdateProblemRequest;

import java.util.List;

public interface ProblemService
{
    public ProblemResponse getProblemById(Long id);
    public ProblemResponse createProblem(CreateProblemRequest problemDTO);
    public ProblemResponse deleteProblem(Long id);
    public List<ProblemResponse> getAllProblems();
    public ProblemResponse updateProblem(Long id, UpdateProblemRequest problemDTO);
}
