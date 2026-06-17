package com.fastlearner.project0.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionRequest;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.dto.judge0.JudgeResult;
import com.fastlearner.project0.dto.judge0.batch.Judge0BatchSubmissionRequest;
import com.fastlearner.project0.dto.judge0.batch.Judge0BatchSubmissionResponse;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.exceptions.UnsupportedLanguageException;
import com.fastlearner.project0.service.Judge0Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class Judge0ServiceImpl implements Judge0Service
{
    private final RestTemplate restTemplate;
    @Value("${judge0.api.url}")
    private String judge0Url;
    private final String batchJudge0Url =
            "http://localhost:2358/submissions/batch?wait=true";
    public Judge0ServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public JudgeResult execute(String sourceCode, Language language, String input)
    {
        Judge0SubmissionRequest request = new Judge0SubmissionRequest();
        request.setSourceCode(sourceCode);
        request.setLanguageId(getJudge0LanguageId(language));
        request.setStdin(input);

        try {
            // 1. Manually force correct serialization using the standard Jackson module
            com.fasterxml.jackson.databind.ObjectMapper standardMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            String jsonPayload = standardMapper.writeValueAsString(request);

            // Debug string to verify your terminal console displays snake_case keys
            System.out.println("Sending clean JSON: " + jsonPayload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 2. Pass the raw JSON string directly instead of the object
            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

            ResponseEntity<Judge0SubmissionResponse> response = restTemplate.exchange(
                    judge0Url,
                    HttpMethod.POST,
                    entity,
                    Judge0SubmissionResponse.class
            );

            Judge0SubmissionResponse body = response.getBody();
            return convertToJudgeResult(body);

        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Judge0 request", e);
        }
    }
    @Override
    public List<JudgeResult> executeBatch(
            String sourceCode,
            Language language,
            List<TestCase> testCases)
    {
        List<Judge0SubmissionRequest> requests =
                testCases.stream()
                        .map(testCase -> {
                            Judge0SubmissionRequest request =
                                    new Judge0SubmissionRequest();

                            request.setSourceCode(sourceCode);
                            request.setLanguageId(
                                    getJudge0LanguageId(language));

                            request.setStdin(
                                    testCase.getInputData());

                            return request;
                        })
                        .toList();

        Judge0BatchSubmissionRequest batchRequest =
                new Judge0BatchSubmissionRequest();

        batchRequest.setSubmissions(requests);

        try
        {
            ObjectMapper mapper = new ObjectMapper();

            String jsonPayload =
                    mapper.writeValueAsString(batchRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(jsonPayload, headers);

            ResponseEntity<Judge0BatchSubmissionResponse> response =
                    restTemplate.exchange(
                            judge0Url + "/batch",
                            HttpMethod.POST,
                            entity,
                            Judge0BatchSubmissionResponse.class
                    );

            Judge0BatchSubmissionResponse body =
                    response.getBody();

            if(body == null || body.getSubmissions() == null)
            {
                throw new RuntimeException(
                        "INVALID_JUDGE0_RESPONSE");
            }

            return body.getSubmissions()
                    .stream()
                    .map(this::convertToJudgeResult)
                    .toList();

        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(
                    "FAILED_TO_SERIALIZE_REQUEST", e);
        }
    }
    @Override
    public Integer getJudge0LanguageId(Language language) {
        switch(language)
        {
            case JAVA:
                return 62;
            case CPP:
                return 54;
            case PYTHON:
                return 100;
            default:
                throw new UnsupportedLanguageException("LANGUAGE_NOT_SUPPORTED");
        }
    }

    @Override
    public JudgeResult convertToJudgeResult(Judge0SubmissionResponse response)
    {
        JudgeResult result = new JudgeResult();
        result.setOutput(response.getStdout());
        if(response.getTime() != null)
        {
            result.setExecutionTimeMs(
                    (long)(Double.parseDouble(
                            response.getTime()) * 1000)
            );
        }
        if(response.getMemory() != null)
        {
            result.setMemoryUsedKb(response.getMemory().longValue());
        }
        result.setVerdict(
                mapVerdict(response)
        );

        return result;
    }

    private Verdict mapVerdict(
            Judge0SubmissionResponse response)
    {
        Integer statusId = response.getStatus().getId();

        return switch (statusId)
        {
            case 3 -> Verdict.ACCEPTED;
            case 5 -> Verdict.TIME_LIMIT_EXCEEDED;
            case 6 -> Verdict.COMPILATION_ERROR;
            case 7,8,9,10,11,12 -> Verdict.RUNTIME_ERROR;
            default -> Verdict.RUNTIME_ERROR;
        };
    }
}
