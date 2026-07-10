package com.fastlearner.project0.serviceImpl.submission;

import com.fastlearner.project0.dto.judge.JudgeDTO;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.enums.TestCaseType;
import com.fastlearner.project0.repository.SubmissionRepository;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.service.submission.SubmissionProcessingService;
import com.fastlearner.project0.serviceImpl.util.SourceCodeFactory;
import com.fastlearner.project0.serviceImpl.util.TestcaseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionProcessingServiceImpl implements SubmissionProcessingService {
    private final SubmissionRepository submissionRepository;
    private final SourceCodeFactory sourceCodeFactory;
    private final JudgeService judgeService;
    private final TestcaseFactory testcaseFactory;

    @Override
    @Async("judgeTaskExecutor") // <-- Change line 50 to this
    public void processBatchSubmissions(List<Long> submissionIds) {
        List<Submission> submissions = submissionRepository.findAllById(submissionIds);
         submissionsToJudge = new ArrayList<>();
        for(Submission submission : submissions)
        {
            Problem problem = submission.getProblem();
            String finalCode = sourceCodeFactory.getSourceCode(problem.getId(), submission.getLanguage(),submission.getSourceCode());
            String input = testcaseFactory.buildTestcases(problem.getId(), TestCaseType.HIDDEN);
            submissionsToJudge.add(new JudgeDTO(finalCode,submission.getLanguage(),input));
        }
        Judge0TokenResponse[] tokens = judgeService.executeBatch(submissionsToJudge);
        for(int i=0;i<tokens.length;i++)
        {
            submissions.get(i).setToken(tokens[i].getToken());
            //submissionRepository.save(submissions.get(i));
        }
// 3. Save EVERYTHING to the database in a single batch call
        submissionRepository.saveAll(submissions); // <-- CHANGE THIS TO saveAll()
    }
}
