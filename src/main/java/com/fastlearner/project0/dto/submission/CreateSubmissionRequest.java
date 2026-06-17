package com.fastlearner.project0.dto.submission;

import com.fastlearner.project0.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubmissionRequest
{
    private Long problemId;
    private Language language;
    private String sourceCode;
}
