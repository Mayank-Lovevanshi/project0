package com.fastlearner.project0.dto.judge0;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeRequest {
    @JsonProperty("source_code")
    private String sourceCode;
    @JsonProperty("language_id")
    private Integer languageId;
    private String stdin;
    @JsonProperty("callback_url")
    private String callbackUrl;
}
