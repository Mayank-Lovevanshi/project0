package com.fastlearner.project0.service.run;

import com.fastlearner.project0.dto.run.RunRequestDTO;
import com.fastlearner.project0.dto.run.RunResponseDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RunService
{
    public CompletableFuture<RunResponseDTO> run(RunRequestDTO runRequest);
}
