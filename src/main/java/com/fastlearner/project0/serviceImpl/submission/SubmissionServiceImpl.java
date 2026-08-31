package com.fastlearner.project0.serviceImpl.submission;
import com.fastlearner.project0.dto.job.SubmissionJob;
import com.fastlearner.project0.dto.submission.CreateSubmissionRequest;
import com.fastlearner.project0.dto.submission.SubmissionResponse;
import com.fastlearner.project0.entity.*;
import com.fastlearner.project0.enums.SubmissionStatus;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.exceptions.AuthenticationException;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.*;
import com.fastlearner.project0.service.problem.ProblemService;
import com.fastlearner.project0.service.submission.SubmissionService;
import com.fastlearner.project0.service.submission.queue.SubmissionExecutionQueue;
import com.fastlearner.project0.serviceImpl.job.SubmissionJobFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ProblemService problemService;
    private final SubmissionJobFactory submissionJobFactory;
    private final SubmissionExecutionQueue executionQueue;

    private void markSubmissionBeforeJudge(Submission submission,User user,Problem problem,CreateSubmissionRequest request)
    {
        submission.setUser(user);
        submission.setProblem(problem);
        submission.setLanguage(request.getLanguage());
        submission.setSourceCode(request.getSourceCode());
        submission.setStatus(SubmissionStatus.PENDING);
        submission.setVerdict(Verdict.PENDING);
        submission.setSubmittedAt(LocalDateTime.now());
    }

    @Override
    public CompletableFuture<SubmissionResponse> submit(CreateSubmissionRequest request)
    {
        System.out.println("Method started");
        Long problemId = request.getProblemId();
        if(problemId == null || problemId <= 0) throw new IllegalArgumentException("INVALID_PROBLEM_ID");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new AuthenticationException("AUTHENTICATION_NEEDED");
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
        Problem problem = problemService.findById(problemId);

        Submission submission = new Submission();
        markSubmissionBeforeJudge(submission,user,problem,request);
        Submission savedSubmission = submissionRepository.save(submission);
        SubmissionJob job = submissionJobFactory.createJob(request,savedSubmission.getId());
        executionQueue.enqueue(job);
        System.out.println("Method finished");
        return job.getFuture();
    }

    @Override
    public SubmissionResponse getSubmissionById(Long id) {
        Submission submission = submissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SUBMISSION_NOT_FOUND"));
        return modelMapper.map(submission,SubmissionResponse.class);
    }

    @Override
    public List<SubmissionResponse> getSubmissionsByProblemId(Long problemId) {
       List<Submission> submissions =  submissionRepository.findByProblemId(problemId);
       return submissions.stream().map(submission -> modelMapper.map( submission,SubmissionResponse.class)).toList();
    }

    @Override
    public List<SubmissionResponse> getMySubmissions() {
        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Submission> submissions = submissionRepository.findByUserEmail(currentUser.getUsername());
        return submissions.stream().map(submission->modelMapper.map(submission,SubmissionResponse.class)).toList();
    }

    @Override
    public Submission findSubmissionById(Long id) {
        return  submissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SUBMISSION_NOT_FOUND"));
    }

    @Override
    public Submission saveSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

}
