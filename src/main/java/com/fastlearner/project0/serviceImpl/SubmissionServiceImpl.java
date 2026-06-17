package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.judge0.JudgeResult;
import com.fastlearner.project0.dto.submission.CreateSubmissionRequest;
import com.fastlearner.project0.dto.submission.SubmissionResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.entity.User;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.exceptions.AuthenticationException;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.ProblemRepository;
import com.fastlearner.project0.repository.SubmissionRepository;
import com.fastlearner.project0.repository.TestCaseRepository;
import com.fastlearner.project0.repository.UserRepository;
import com.fastlearner.project0.service.Judge0Service;
import com.fastlearner.project0.service.JudgeService;
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
    private final TestCaseRepository testCaseRepository;
    private final Judge0Service judgeService;
    public SubmissionServiceImpl(SubmissionRepository submissionRepository, ModelMapper modelMapper, UserRepository userRepository, ProblemRepository problemRepository, TestCaseRepository testCaseRepository, Judge0Service judgeService) {
        this.submissionRepository = submissionRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
        this.testCaseRepository = testCaseRepository;
        this.judgeService = judgeService;
    }
    @Override
    public SubmissionResponse submit(
            CreateSubmissionRequest request)
    {
        Long problemId = request.getProblemId();

        if(problemId == null || problemId <= 0)
        {
            throw new IllegalArgumentException(
                    "INVALID_PROBLEM_ID");
        }

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if(authentication == null)
        {
            throw new AuthenticationException(
                    "AUTHENTICATION_NEEDED");
        }

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "USER_NOT_FOUND"));

        Problem problem = problemRepository
                .findById(problemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PROBLEM_NOT_FOUND"));

        List<TestCase> testCases =
                testCaseRepository
                        .findByProblemId(problemId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "TEST_CASE_NOT_FOUND"));

        Submission submission = new Submission();

        submission.setUser(user);
        submission.setProblem(problem);
        submission.setLanguage(request.getLanguage());
        submission.setSourceCode(request.getSourceCode());
        submission.setVerdict(Verdict.PENDING);
        submission.setSubmittedAt(LocalDateTime.now());

        submission = submissionRepository.save(submission);

        List<JudgeResult> results =
                judgeService.executeBatch(
                        request.getSourceCode(),
                        request.getLanguage(),
                        testCases
                );

        Verdict finalVerdict = Verdict.ACCEPTED;

        int passedTestCases = 0;

        long maxExecutionTime = 0L;
        long maxMemoryUsed = 0L;

        for(int i = 0; i < results.size(); i++)
        {
            JudgeResult result = results.get(i);

            TestCase testCase = testCases.get(i);

            if(result.getExecutionTimeMs() != null)
            {
                maxExecutionTime = Math.max(
                        maxExecutionTime,
                        result.getExecutionTimeMs()
                );
            }

            if(result.getMemoryUsedKb() != null)
            {
                maxMemoryUsed = Math.max(
                        maxMemoryUsed,
                        result.getMemoryUsedKb()
                );
            }

            /*
             * Judge0 level failures
             */
            if(result.getVerdict() != Verdict.ACCEPTED)
            {
                finalVerdict = result.getVerdict();
                break;
            }

            /*
             * Compare actual output vs expected output
             */
            String actualOutput =
                    result.getOutput() == null
                            ? ""
                            : result.getOutput().trim();

            String expectedOutput =
                    testCase.getExpectedOutput() == null
                            ? ""
                            : testCase.getExpectedOutput().trim();

            if(!actualOutput.equals(expectedOutput))
            {
                finalVerdict = Verdict.WRONG_ANSWER;
                break;
            }

            passedTestCases++;
        }

        submission.setVerdict(finalVerdict);
        submission.setPassedTestCases(passedTestCases);
        submission.setTotalTestCases(testCases.size());
        submission.setExecutionTimeMs(maxExecutionTime);
        submission.setMemoryUsedKb(maxMemoryUsed);

        submission = submissionRepository.save(submission);

        return modelMapper.map(
                submission,
                SubmissionResponse.class
        );
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
