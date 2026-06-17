package com.fastlearner.project0.serviceImpl;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionRequest;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.dto.judge0.JudgeResult;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.exceptions.UnsupportedLanguageException;
import com.fastlearner.project0.service.Judge0Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class Judge0ServiceImpl implements Judge0Service
{
    private final RestTemplate restTemplate;
    @Value("${judge0.api.url}")
    private String judge0Url;
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
            ObjectMapper standardMapper = new ObjectMapper();
            String jsonPayload = standardMapper.writeValueAsString(request);

            // Debug string to verify your terminal console displays snake_case keys
            System.out.println("Sending clean JSON: " + jsonPayload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 2. Pass the raw JSON string directly instead of the object
            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);
            Long start = System.currentTimeMillis();
            ResponseEntity<Judge0SubmissionResponse> response = restTemplate.exchange(
                    judge0Url,
                    HttpMethod.POST,
                    entity,
                    Judge0SubmissionResponse.class
            );
            System.out.println(System.currentTimeMillis()-start);
            Judge0SubmissionResponse body = response.getBody();
            return convertToJudgeResult(body);

        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Judge0 request", e);
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
