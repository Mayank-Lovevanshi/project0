package com.fastlearner.project0.controller;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.service.submission.SubmissionEvaluatorService;
import com.fastlearner.project0.service.util.DecoderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/judge")
@ResponseStatus(HttpStatus.OK)
@RequiredArgsConstructor
public class JudgeController
{
    private final DecoderService decoderService;
    private final SubmissionEvaluatorService submissionEvaluatorService;
    @PutMapping("/submission")
    public void saveSubmission(@RequestBody Judge0SubmissionResponse body)
    {
        decoderService.decode(body);
        submissionEvaluatorService.evaluateResponse(body);
    }
    @PutMapping("/run")
    public void runResult(@RequestBody Judge0SubmissionResponse body)
    {
        decoderService.decode(body);
    }

}
