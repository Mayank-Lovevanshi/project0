package com.fastlearner.project0.dto.run;

import com.fastlearner.project0.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunRequestDTO
{
    private Long problemId;
    private Language language;
    private String sourceCode;
}
