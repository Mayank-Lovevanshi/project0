package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.auth.LoginDTO;
import com.fastlearner.project0.dto.auth.LoginResponseDTO;
import com.fastlearner.project0.dto.auth.RegisterDTO;
import com.fastlearner.project0.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterDTO> register(@RequestBody @Valid RegisterDTO registerDTO)
    {
        return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO)
    {
        return new ResponseEntity<>(authService.login(loginDTO),HttpStatus.OK);
    }
}
