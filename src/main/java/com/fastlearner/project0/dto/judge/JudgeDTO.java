package com.fastlearner.project0.dto.judge;

import com.fastlearner.project0.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeDTO
{
    private String finalCode;
    private Language language;
    private String input;
}
