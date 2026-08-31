package com.fastlearner.project0.service.problem;

import com.fastlearner.project0.dto.problem.CreateProblemRequest;
import com.fastlearner.project0.dto.problem.ProblemResponse;
import com.fastlearner.project0.dto.problem.UpdateProblemRequest;
import com.fastlearner.project0.entity.Problem;

import java.util.List;

public interface ProblemService
{
     ProblemResponse getProblemById(Long id);
     ProblemResponse createProblem(CreateProblemRequest problemDTO);
     ProblemResponse deleteProblem(Long id);
     List<ProblemResponse> getAllProblems();
     ProblemResponse updateProblem(Long id, UpdateProblemRequest problemDTO);
    Problem findById(Long id);
}
