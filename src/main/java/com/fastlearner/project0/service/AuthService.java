package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.LoginDTO;
import com.fastlearner.project0.dto.LoginResponseDTO;
import com.fastlearner.project0.dto.RegisterDTO;

public interface AuthService
{
    public RegisterDTO register(RegisterDTO registerDTO);
    public LoginResponseDTO login(LoginDTO loginDTO);
}
