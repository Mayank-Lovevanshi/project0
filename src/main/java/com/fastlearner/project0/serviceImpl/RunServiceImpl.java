package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.run.RunRequestDTO;
import com.fastlearner.project0.dto.run.RunResponseDTO;
import com.fastlearner.project0.repository.TestCaseRepository;
import com.fastlearner.project0.service.ProblemService;
import com.fastlearner.project0.service.RunService;
import org.springframework.stereotype.Service;

@Service
public class RunServiceImpl implements RunService
{
    private final TestCaseRepository testCaseRepository;
    private final ProblemService problemService;
    public RunServiceImpl(TestCaseRepository testCaseRepository, ProblemService problemService) {
        this.testCaseRepository = testCaseRepository;
        this.problemService = problemService;
    }

    @Override
    public RunResponseDTO run(RunRequestDTO runRequest) {
        return null;
    }
}
