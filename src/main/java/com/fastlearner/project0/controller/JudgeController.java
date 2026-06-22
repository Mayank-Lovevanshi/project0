package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.judge0.Judge0Status;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.service.SubmissionEvaluatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/judge0")
public class JudgeController
{
    private final SubmissionEvaluatorService submissionEvaluatorService;

    public JudgeController(SubmissionEvaluatorService submissionEvaluatorService) {
        this.submissionEvaluatorService = submissionEvaluatorService;
    }
    /*
    public class Judge0SubmissionResponse
    {
        private String stdout;
        private String stderr;
        private String compile_output;
        private Double time;
        private Long memory;
        private Judge0Status status;
    }
     */
/*
    public class JudgeResult
    {
        private String output;
        private String executionTimeMs;
        private Integer memoryUsedKb;
        private Verdict verdict;
        private String errorMessage;
    }
 */
    @PutMapping("/result")
    public ResponseEntity<Void> saveResult(@RequestBody Judge0SubmissionResponse body)
    {
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println(body);
        System.out.println("+++++++++++++++++++++++++++++++");
        body.setStdout(body.getStdout().trim());
        submissionEvaluatorService.evaluateResponse(body);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public void test()
    {
        System.out.println("CAME!!!");
    }
}
