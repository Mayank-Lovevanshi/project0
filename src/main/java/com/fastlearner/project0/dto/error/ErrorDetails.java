package com.fastlearner.project0.dto.error;


import com.fastlearner.project0.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails
{
    private ErrorType errorType;
    private String message;
}
