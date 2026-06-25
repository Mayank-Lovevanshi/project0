package com.fastlearner.project0.serviceImpl.submission;

import com.fastlearner.project0.dto.judge.JudgeDTO;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.entity.Submission;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.exceptions.ResourceNotFoundException;
import com.fastlearner.project0.repository.StructureRepository;
import com.fastlearner.project0.repository.SubmissionRepository;
import com.fastlearner.project0.repository.TestCaseRepository;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.service.submission.SubmissionProcessingService;
import com.fastlearner.project0.service.util.CodeGeneratorService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmissionProcessingServiceImpl implements SubmissionProcessingService {
    private final SubmissionRepository submissionRepository;
    private final StructureRepository structureRepository;
    private final TestCaseRepository testCaseRepository;
    private final JudgeService judgeService;
    private final CodeGeneratorService codeGeneratorService;

    public SubmissionProcessingServiceImpl(SubmissionRepository submissionRepository, StructureRepository structureRepository, TestCaseRepository testCaseRepository, JudgeService judgeService, CodeGeneratorService codeGeneratorService) {
        this.submissionRepository = submissionRepository;
        this.structureRepository = structureRepository;
        this.testCaseRepository = testCaseRepository;
        this.judgeService = judgeService;
        this.codeGeneratorService = codeGeneratorService;
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
            Structure structure = structureRepository.findByProblem(problem).orElseThrow(()->new ResourceNotFoundException("DRIVER_CODE_NOT_FOUND_SubmissionServiceImpl"));
            String driverCode = codeGeneratorService.generateDriverCode(structure, language);
            String inputParserCode = codeGeneratorService.generateInputUtilityCode(language);
            String finalCode = driverCode+"\n"+sourceCode+"\n"+inputParserCode;
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
