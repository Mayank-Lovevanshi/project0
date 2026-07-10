package com.fastlearner.project0.serviceImpl.submission;

import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.enums.SubmissionStatus;
import com.fastlearner.project0.repository.SubmissionRepository;
import com.fastlearner.project0.service.submission.SubmissionProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubmissionSchedulerImpl
{
    private final SubmissionRepository submissionRepository;
    private final SubmissionProcessingService submissionProcessingService;
    @Scheduled(fixedDelay = 1000)
    public void processPendingSubmissions()
    {
        List<Submission> submissions = submissionRepository.findSubmissionsToJudge();
        if(submissions.isEmpty()) return;
        List<Long> submissionIds = submissions.stream().map(this::processSubmission).toList();
        submissionRepository.saveAll(submissions);
        submissionProcessingService.processBatchSubmissions(submissionIds);
    }
    private Long processSubmission(Submission submission)
    {
        submission.setStatus(SubmissionStatus.RUNNING);
        submission.setStartedAt(LocalDateTime.now());
        return submission.getId();
    }
}
