package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.judge0.Judge0SubmissionRequest;
import com.fastlearner.project0.dto.judge0.JudgeResult;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.Judge0Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Judge0Controller
{
    private final Judge0Service judge0Service;

    public Judge0Controller(Judge0Service judge0Service) {
        this.judge0Service = judge0Service;
    }

    @PostMapping("/judge0/test")
    public ResponseEntity<JudgeResult> execute(@RequestBody Judge0SubmissionRequest request)
    {
        System.out.println(request);
        return new ResponseEntity<>(judge0Service.execute(request.getSourceCode(), Language.JAVA, request.getStdin()), HttpStatus.OK);
    }
}
