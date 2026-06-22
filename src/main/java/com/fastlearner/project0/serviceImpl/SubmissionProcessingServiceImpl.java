package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.judge.JudgeDTO;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.ProblemTemplate;
import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.ProblemTemplateRepository;
import com.fastlearner.project0.repository.SubmissionRepository;
import com.fastlearner.project0.repository.TestCaseRepository;
import com.fastlearner.project0.service.JudgeService;
import com.fastlearner.project0.service.SubmissionProcessingService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmissionProcessingServiceImpl implements SubmissionProcessingService {
    private final SubmissionRepository submissionRepository;
    private final ProblemTemplateRepository problemTemplateRepository;
    private final TestCaseRepository testCaseRepository;
    private final JudgeService judgeService;

    public SubmissionProcessingServiceImpl(SubmissionRepository submissionRepository, ProblemTemplateRepository problemTemplateRepository, TestCaseRepository testCaseRepository, JudgeService judgeService) {
        this.submissionRepository = submissionRepository;
        this.problemTemplateRepository = problemTemplateRepository;
        this.testCaseRepository = testCaseRepository;
        this.judgeService = judgeService;
    }
    private void applyTestCasesData(List<TestCase> testCases,StringBuilder input)
    {
        input.append(testCases.size());
        input.append("\n");
        for(TestCase testCase : testCases)
        {
            input.append(testCase.getInputData());
            input.append("\n");
        }
    }

    @Override
    @Async
    public void processBatchSubmissions(List<Long> submissionIds) {
        List<Submission> submissions = submissionRepository.findAllById(submissionIds);
        List<JudgeDTO> submissionsToJudge = new ArrayList<>();
        for(Submission submission : submissions)
        {
            Problem problem = submission.getProblem();
            Language language = submission.getLanguage();
            String sourceCode = submission.getSourceCode();
            ProblemTemplate problemTemplate = problemTemplateRepository.findByProblemAndLanguage(problem,language).orElseThrow(()->new ResourceNotFoundException("DRIVER_CODE_NOT_FOUND_SubmissionServiceImpl"));
            String driverCode = problemTemplate.getDriverCode();
            String finalCode = driverCode+"\n"+sourceCode;
            List<TestCase> testCases = testCaseRepository.findByProblemId(problem.getId()).orElseThrow(() -> new ResourceNotFoundException("TEST_CASE_NOT_FOUND"));
            StringBuilder input = new StringBuilder();
            applyTestCasesData(testCases,input);
            submissionsToJudge.add(new JudgeDTO(finalCode,language,input.toString()));
        }
        Judge0TokenResponse[] tokens = judgeService.executeBatch(submissionsToJudge);
        for(int i=0;i<tokens.length;i++)
        {
            submissions.get(i).setToken(tokens[i].getToken());
            submissionRepository.save(submissions.get(i));
        }
    }
}
