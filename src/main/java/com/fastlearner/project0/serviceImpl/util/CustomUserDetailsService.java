package com.fastlearner.project0.serviceImpl.util;

import com.fastlearner.project0.entity.CustomUserDetails;
import com.fastlearner.project0.entity.User;
import com.fastlearner.project0.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    public UserDetails loadUserByUsername(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("USER_NOT_FOUND"));
        return new CustomUserDetails(user);
    }


}
