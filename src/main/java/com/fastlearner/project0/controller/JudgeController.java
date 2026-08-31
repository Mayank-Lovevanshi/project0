package com.fastlearner.project0.controller;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.service.run.evaluation.RunEvaluatorService;
import com.fastlearner.project0.service.submission.evaluation.SubmissionEvaluatorService;
import com.fastlearner.project0.service.util.DecoderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
///api/judge/submission
@RestController
@RequestMapping("/api/judge")
@ResponseStatus(HttpStatus.OK)
@RequiredArgsConstructor
public class JudgeController
{
    private final DecoderService decoderService;
    private final SubmissionEvaluatorService submissionEvaluatorService;
    private final RunEvaluatorService runEvaluatorService;
    @PutMapping("/submission")
    public void saveSubmission(@RequestBody Judge0SubmissionResponse body)
    {
        System.out.println("save submission from Judge");
        decoderService.decode(body);
        submissionEvaluatorService.evaluateResponse(body);
    }
    @PutMapping("/run")
    public void runResult(@RequestBody Judge0SubmissionResponse body)
    {
        System.out.println("Arrived");
        decoderService.decode(body);
        runEvaluatorService.evaluate(body);
    }

}
