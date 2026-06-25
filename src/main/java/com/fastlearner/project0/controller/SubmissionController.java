package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.submission.CreateSubmissionRequest;
import com.fastlearner.project0.dto.submission.SubmissionResponse;
import com.fastlearner.project0.service.submission.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubmissionController
{
    private final SubmissionService submissionService;
    public SubmissionController(SubmissionService submissionService)
    {
        this.submissionService = submissionService;
    }
    @PostMapping("/submissions")
    public ResponseEntity<SubmissionResponse> submit(@RequestBody @Valid CreateSubmissionRequest createSubmissionRequest)
    {
        return new ResponseEntity<>(submissionService.submit(createSubmissionRequest), HttpStatus.OK);
    }
    @GetMapping("/submissions/{id}")
    public ResponseEntity<SubmissionResponse> getSubmission(@PathVariable Long id)
    {
        return new ResponseEntity<>(submissionService.getSubmissionById(id),HttpStatus.OK);
    }
    @GetMapping("/problems/{problemId}/submissions")
    public ResponseEntity<List<SubmissionResponse>> getSubmissionsByProblemId(@PathVariable Long problemId)
    {
        return new ResponseEntity<>(submissionService.getSubmissionsByProblemId(problemId),HttpStatus.OK);
    }
    @GetMapping("/submissions/my")
    public ResponseEntity<List<SubmissionResponse>> getMySubmissions()
    {
        return new ResponseEntity<>(submissionService.getMySubmissions(),HttpStatus.OK);
    }
}
