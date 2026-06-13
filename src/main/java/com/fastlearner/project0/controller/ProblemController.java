package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.CreateProblemRequest;
import com.fastlearner.project0.dto.ProblemResponse;
import com.fastlearner.project0.service.ProblemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/problems")
@RestController
public class ProblemController
{
    private final ProblemService problemService;
    public ProblemController(ProblemService problemService)
    {
        this.problemService = problemService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProblemResponse> getProblemById(@PathVariable Long id)
    {
        return new ResponseEntity<>(problemService.getProblemById(id), HttpStatus.OK);
    }
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProblemResponse> createProblem(@RequestBody CreateProblemRequest problemDTO)
    {
        return new ResponseEntity<>(problemService.createProblem(problemDTO),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProblemResponse> deleteProblem(@PathVariable Long id)
    {
        return new ResponseEntity<>(problemService.deleteProblem(id),HttpStatus.OK);
    }

}
