package com.fastlearner.project0.service.auth;

import com.fastlearner.project0.dto.auth.LoginDTO;
import com.fastlearner.project0.dto.auth.LoginResponseDTO;
import com.fastlearner.project0.dto.auth.RegisterDTO;

public interface AuthService
{
    public RegisterDTO register(RegisterDTO registerDTO);
    public LoginResponseDTO login(LoginDTO loginDTO);
}
