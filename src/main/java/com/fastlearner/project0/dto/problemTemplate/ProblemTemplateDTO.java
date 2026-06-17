package com.fastlearner.project0.dto.problemTemplate;
import com.fastlearner.project0.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemTemplateDTO
{
    private Language language;
    private String starterCode;
    private String driverCode;
}
