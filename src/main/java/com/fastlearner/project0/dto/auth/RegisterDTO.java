package com.fastlearner.project0.dto.auth;

import lombok.Data;

@Data
public class RegisterDTO
{
    private String name;
    private String email;
    private String password;
}
