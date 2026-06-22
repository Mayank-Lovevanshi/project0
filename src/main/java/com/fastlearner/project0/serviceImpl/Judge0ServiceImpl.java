package com.fastlearner.project0.serviceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fastlearner.project0.dto.judge.JudgeDTO;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionRequest;
import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.dto.judge0.JudgeResult;
import com.fastlearner.project0.dto.judge0.batch.Judge0BatchSubmissionRequest;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.enums.Verdict;
import com.fastlearner.project0.exceptions.UnsupportedLanguageException;
import com.fastlearner.project0.service.JudgeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


@Service
public class Judge0ServiceImpl implements JudgeService
{
    private final RestTemplate restTemplate;
    @Value("${judge0.api.url}")
    private String judge0Url;
    @Value("${judge0.api.callbackUrl}")
    private String callbackUrl;
    private final ObjectMapper objectMapper;
    public Judge0ServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public String execute(String sourceCode, Language language, String input)
    {
        Judge0SubmissionRequest request = new Judge0SubmissionRequest();
        request.setSourceCode(sourceCode);
        request.setLanguageId(getJudge0LanguageId(language));
        request.setStdin(input);

        try {
            String jsonPayload = objectMapper.writeValueAsString(request);
//            System.out.println("SENDING DATA : "+jsonPayload);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 2. Pass the raw JSON string directly instead of the object
            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);
            long start = System.currentTimeMillis();
            ResponseEntity<Judge0TokenResponse> response = restTemplate.exchange(
                    judge0Url,
                    HttpMethod.POST,
                    entity,
                    Judge0TokenResponse.class
            );
//            System.out.println("RESPONSE : "+response);
            return response.getBody().getToken();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Judge0 request", e);
        }
    }
    @Override
    public JudgeResult getResult(String token)
    {
        String url = judge0Url + "/" + token;
        System.out.println("SENDING DATA TO : "+url);
        Long start = System.currentTimeMillis();
        while(true)
        {
            ResponseEntity<Judge0SubmissionResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    Judge0SubmissionResponse.class
            );
            Judge0SubmissionResponse judge0SubmissionResponse = response.getBody();
            int statusId = judge0SubmissionResponse.getStatus().getId();
            System.out.println("STATUS ID : "+statusId);
            if(statusId!=1 && statusId!=2)
            {
                System.out.println("RESPONSE_TIME : "+(System.currentTimeMillis()-start));
                return convertToJudgeResult(judge0SubmissionResponse);
            }
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
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
        /*
        private String output;
        private Long executionTimeMs;
        private Long memoryUsedKb;
        private Verdict verdict;
        private String errorMessage;
         */
        JudgeResult result = new JudgeResult();
        result.setOutput(response.getStdout());
        result.setVerdict(mapVerdict(response));
        result.setMemoryUsedKb(response.getMemory()==null?0:response.getMemory());
        result.setExecutionTimeMs(response.getTime()==null?0:response.getTime());
        if(result.getVerdict().equals(Verdict.RUNTIME_ERROR))
        {
            result.setErrorMessage(response.getStderr());
        }
        else
        {
            result.setErrorMessage(response.getCompile_output());
        }
        return result;
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
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
            return response.getBody();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Judge0 request", e);
        }
    }

    private Verdict mapVerdict(Judge0SubmissionResponse response)
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
