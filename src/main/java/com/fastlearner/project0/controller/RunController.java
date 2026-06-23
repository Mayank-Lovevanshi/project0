package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.run.RunRequestDTO;
import com.fastlearner.project0.dto.run.RunResponseDTO;
import com.fastlearner.project0.dto.submission.CreateSubmissionRequest;
import com.fastlearner.project0.dto.submission.SubmissionResponse;
import com.fastlearner.project0.service.RunService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/run")
public class RunController
{
    private final RunService runService;
    public RunController(RunService runService) {
        this.runService = runService;
    }

    @PostMapping
    public ResponseEntity<RunResponseDTO> run(@RequestBody @Valid RunRequestDTO runRequest)
    {
        return new ResponseEntity<>(runService.run(runRequest), HttpStatus.OK);
    }
}
