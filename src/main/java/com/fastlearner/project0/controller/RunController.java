package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.run.RunRequestDTO;
import com.fastlearner.project0.dto.run.RunResponseDTO;
import com.fastlearner.project0.service.run.RunService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/run")
@RequiredArgsConstructor
public class RunController
{
    private final RunService runService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<RunResponseDTO> run(@RequestBody @Valid RunRequestDTO runRequest)
    {
        return runService.run(runRequest);
    }

}
/*
Flow of this Controller
1. Controller
2. Queue me daal do aur CompletableFuture return kar do
3.
 */