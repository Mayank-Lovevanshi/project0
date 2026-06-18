package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.evaluation.EvaluationResult;
import com.fastlearner.project0.dto.submission.CreateSubmissionRequest;
import com.fastlearner.project0.dto.submission.SubmissionResponse;
import com.fastlearner.project0.entity.*;
import com.fastlearner.project0.enums.SubmissionStatus;
import com.fastlearner.project0.exceptions.AuthenticationException;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.*;
import com.fastlearner.project0.service.SubmissionProcessingService;
import com.fastlearner.project0.service.SubmissionService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final SubmissionProcessingService submissionProcessingService;
    public SubmissionServiceImpl(SubmissionRepository submissionRepository, ModelMapper modelMapper, UserRepository userRepository, ProblemRepository problemRepository,  SubmissionProcessingService submissionProcessingService) {
        this.submissionRepository = submissionRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
        this.submissionProcessingService = submissionProcessingService;
    }

    private void markSubmissionBeforeJudge(Submission submission,User user,Problem problem,CreateSubmissionRequest request)
    {
        submission.setUser(user);
        submission.setProblem(problem);
        submission.setLanguage(request.getLanguage());
        submission.setSourceCode(request.getSourceCode());
        submission.setStatus(SubmissionStatus.PENDING);
        submission.setVerdict(null);
        submission.setSubmittedAt(LocalDateTime.now());
    }

    @Override
    public SubmissionResponse submit(CreateSubmissionRequest request)
    {
        Long problemId = request.getProblemId();
        if(problemId == null || problemId <= 0) throw new IllegalArgumentException("INVALID_PROBLEM_ID");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new AuthenticationException("AUTHENTICATION_NEEDED");

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
        Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new ResourceNotFoundException("PROBLEM_NOT_FOUND"));
        Submission submission = new Submission();
        markSubmissionBeforeJudge(submission,user,problem,request);
        submissionRepository.save(submission);
        submissionProcessingService.processSubmission(submission.getId());
        return modelMapper.map(submission,SubmissionResponse.class);
    }

    @Override
    public SubmissionResponse getSubmissionById(Long id) {
        return null;
    }

    @Override
    public List<SubmissionResponse> getSubmissionsByProblemId(Long problemId) {
        return null;
    }

    @Override
    public List<SubmissionResponse> getMySubmissions() {
        return null;
    }
}
