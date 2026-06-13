package com.fastlearner.project0.serviceImpl;

import com.fastlearner.project0.dto.LoginDTO;
import com.fastlearner.project0.dto.LoginResponseDTO;
import com.fastlearner.project0.dto.RegisterDTO;
import com.fastlearner.project0.entity.User;
import com.fastlearner.project0.enums.Role;
import com.fastlearner.project0.exceptions.AuthenticationException;
import com.fastlearner.project0.repository.UserRepository;
import com.fastlearner.project0.service.AuthService;
import com.fastlearner.project0.service.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, ModelMapper modelMapper,AuthenticationManager authenticationManager,PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    @Override
    public RegisterDTO register(RegisterDTO registerDTO) {
       String email = registerDTO.getEmail();
       Optional<User> optionalUser = userRepository.findByEmail(email);
       if(optionalUser.isPresent()){
           throw new RuntimeException("USER_ALREADY_EXISTS");
       }
       User user = modelMapper.map(registerDTO,User.class);
       user.setRole(Role.ADMIN);
       user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
       return modelMapper.map(userRepository.save(user),RegisterDTO.class);
    }
    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authenticationObject =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        if(authenticationObject.isAuthenticated()){
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            String token = jwtService.generateToken(loginDTO);
            loginResponseDTO.setToken(token);
            return loginResponseDTO;
        }
        throw new AuthenticationException("NOT_AUTHENTICATED");
    }
}
