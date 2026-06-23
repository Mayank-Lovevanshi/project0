package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.run.RunRequestDTO;
import com.fastlearner.project0.dto.run.RunResponseDTO;
import jakarta.validation.Valid;

public interface RunService
{
    public RunResponseDTO run(RunRequestDTO runRequest);
}
