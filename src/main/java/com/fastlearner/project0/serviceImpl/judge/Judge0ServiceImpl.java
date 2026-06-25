package com.fastlearner.project0.serviceImpl.judge;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fastlearner.project0.dto.judge.JudgeDTO;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionRequest;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;

import com.fastlearner.project0.dto.judge0.batch.Judge0BatchSubmissionRequest;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.exceptions.UnsupportedLanguageException;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.service.util.CodeGeneratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class Judge0ServiceImpl implements JudgeService
{
    private final RestTemplate restTemplate;
    @Value("${judge0.api.url}")
    private String judge0Url;
    @Value("${judge0.api.callbackUrl}")
    private String callbackUrl;
    private final ObjectMapper objectMapper;
    private final CodeGeneratorService codeGeneratorService;
    public Judge0ServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper, CodeGeneratorService codeGeneratorService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.codeGeneratorService = codeGeneratorService;
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
    public Judge0TokenResponse[] executeBatch(List<JudgeDTO> submissionsToJudge) {
        Judge0BatchSubmissionRequest batchRequest = new Judge0BatchSubmissionRequest();
        List<Judge0SubmissionRequest> request = new ArrayList<>();
        for(JudgeDTO submission : submissionsToJudge)
        {
            int languageId = getJudge0LanguageId(submission.getLanguage());
            String sourceCode = submission.getFinalCode();
            String input = submission.getInput();
            Judge0SubmissionRequest submissionRequest = new Judge0SubmissionRequest();
            submissionRequest.setLanguageId(languageId);
            submissionRequest.setStdin(input);
            submissionRequest.setSourceCode(sourceCode);
            submissionRequest.setCallbackUrl(callbackUrl);
            request.add(submissionRequest);
        }
        batchRequest.setSubmissions(request);
        try {
            String jsonPayload = objectMapper.writeValueAsString(batchRequest);
            System.out.println("SENDING DATA : "+jsonPayload);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 2. Pass the raw JSON string directly instead of the object
            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);
            long start = System.currentTimeMillis();
            System.out.println("JUDGE URL : "+judge0Url);
            ResponseEntity<Judge0TokenResponse[]> response = restTemplate.exchange(
                    judge0Url,
                    HttpMethod.POST,
                    entity,
                    Judge0TokenResponse[].class
            );
//            System.out.println("RESPONSE : "+response);
            for(int i=0;i<response.getBody().length;i++)
            {
                System.out.println("TOKEN : "+response.getBody()[i]);
            }

            return response.getBody();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Judge0 request", e);
        }
    }
}
