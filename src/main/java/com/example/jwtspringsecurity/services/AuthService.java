package com.example.jwtspringsecurity.services;

import com.example.jwtspringsecurity.dto.SignUpRequest;
import org.springframework.stereotype.Service;


public interface AuthService {
    boolean createUser(SignUpRequest signUpRequest);
}
