package com.fastlearner.project0.serviceImpl.util;

import com.fastlearner.project0.dto.judge0.Judge0SubmissionResponse;
import com.fastlearner.project0.service.util.DecoderService;
import org.springframework.stereotype.Service;

import java.util.Base64;
@Service
public class DecoderServiceImpl implements DecoderService
{

    private String decode(String value)
    {
        if(value == null) return null;
        return new String(
                Base64.getMimeDecoder().decode(value.trim())
        );
    }
    @Override
    public void decode(Judge0SubmissionResponse response) {
        response.setStdout(decode(response.getStdout()));
        response.setStderr(decode(response.getStderr()));
        response.setCompileOutput(decode(response.getCompileOutput()));
        response.setMessage(decode(response.getMessage()));
        System.out.println("RESPONSE : "+response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
    }
}
