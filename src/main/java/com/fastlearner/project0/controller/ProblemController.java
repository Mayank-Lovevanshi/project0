package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.problem.CreateProblemRequest;
import com.fastlearner.project0.dto.problem.ProblemResponse;
import com.fastlearner.project0.dto.problem.UpdateProblemRequest;
import com.fastlearner.project0.service.problem.ProblemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ProblemResponse> createProblem(@RequestBody @Valid CreateProblemRequest problemDTO)
    {
        return new ResponseEntity<>(problemService.createProblem(problemDTO),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProblemResponse> deleteProblem(@PathVariable Long id)
    {
        return new ResponseEntity<>(problemService.deleteProblem(id),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ProblemResponse>> getAllProblems()
    {
        return new ResponseEntity<>(problemService.getAllProblems(),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProblemResponse> updateProblem(@PathVariable Long id,@RequestBody @Valid UpdateProblemRequest problemDTO)
    {
        return new ResponseEntity<>(problemService.updateProblem(id,problemDTO),HttpStatus.OK);
    }

}
