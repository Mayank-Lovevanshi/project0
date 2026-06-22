package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.judge0.Judge0SubmissionRequest;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.dto.judge0.JudgeResult;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.JudgeService;
import com.fastlearner.project0.service.SubmissionEvaluatorService;
import com.fastlearner.project0.service.SubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/judge0")
public class Judge0Controller
{
    private final SubmissionEvaluatorService submissionEvaluatorService;

    public Judge0Controller(SubmissionEvaluatorService submissionEvaluatorService) {
        this.submissionEvaluatorService = submissionEvaluatorService;
    }

    @PutMapping("/result")
    public void saveResult(@RequestBody Judge0SubmissionResponse judge0SubmissionResponse)
    {
        System.out.println("CAME!!!");
        //submissionEvaluatorService.evaluate()
    }
    @PutMapping
    public void test()
    {
        System.out.println("CAME!!!");
    }
}
