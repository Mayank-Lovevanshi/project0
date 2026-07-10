package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.submission.CreateSubmissionRequest;
import com.fastlearner.project0.dto.submission.SubmissionResponse;
import com.fastlearner.project0.service.submission.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@RequiredArgsConstructor
public class SubmissionController
{
    private final SubmissionService submissionService;
    @PostMapping("/submissions")
    public CompletableFuture<SubmissionResponse> submit(@RequestBody @Valid CreateSubmissionRequest createSubmissionRequest)
    {
        return submissionService.submit(createSubmissionRequest);
    }
    @GetMapping("/submissions/{id}")
    public SubmissionResponse getSubmission(@PathVariable Long id)
    {
        return submissionService.getSubmissionById(id);
    }
    @GetMapping("/problems/{problemId}/submissions")
    public List<SubmissionResponse> getSubmissionsByProblemId(@PathVariable Long problemId)
    {
        return submissionService.getSubmissionsByProblemId(problemId);
    }
    @GetMapping("/submissions/my")
    public List<SubmissionResponse> getMySubmissions()
    {
        return submissionService.getMySubmissions();
    }
}
