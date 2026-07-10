package com.fastlearner.project0.serviceImpl.judge;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fastlearner.project0.dto.job.Job;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;

import com.fastlearner.project0.dto.judge0.batch.Judge0BatchSubmissionRequest;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.exceptions.UnsupportedLanguageException;
import com.fastlearner.project0.service.judge.JudgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class Judge0ServiceImpl implements JudgeService
{
    private final RestTemplate restTemplate;
    @Value("${judge0.api.url}")
    private String judge0Url;
    @Value("${judge0.api.callbackUrl}")
    private String callbackUrl;
    private final ObjectMapper objectMapper;
    @Override
    public Integer getJudgeLanguageId(Language language) {
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
    public Judge0TokenResponse[] executeBatch(List<Job<?>> jobs)
    {
        List<JudgeRequest> judgeRequests = jobs.stream().map(job -> job.getJudgeRequest()).toList();
        Judge0BatchSubmissionRequest batchRequest = new Judge0BatchSubmissionRequest();
        batchRequest.setSubmissions(judgeRequests);
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
            for(int i=0;i<response.getBody().length;i++)
            {
                System.out.println("TOKEN : "+response.getBody()[i]);
            }

            return response.getBody();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Judge0 request", e);
        }
    }
    /*
    @Override
    public Judge0TokenResponse[] executeBatch(List<JudgeDTO> submissionsToJudge) {
        Judge0BatchSubmissionRequest batchRequest = new Judge0BatchSubmissionRequest();
        List<JudgeRequest> request = new ArrayList<>();
        for(JudgeDTO submission : submissionsToJudge)
        {
            int languageId = getJudgeLanguageId(submission.getLanguage());
            String sourceCode = submission.getFinalCode();
            String input = submission.getInput();
            JudgeRequest submissionRequest = new JudgeRequest();
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

     */
}
