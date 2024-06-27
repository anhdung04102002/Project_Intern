package com.example.jwtspringsecurity.controller;

import com.example.jwtspringsecurity.dto.SignUpRequest;
import com.example.jwtspringsecurity.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class SignUpController {
    @Autowired
    private AuthService authService;

    public SignUpController(AuthService authService) {
        this.authService = authService;
    }
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        boolean isUserCreated = authService.createUser(signUpRequest);
        if(isUserCreated){
            return ResponseEntity.status(HttpStatus.CREATED).body("Tạo tài khoàn khách hàng thành công");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tài khoản đã tồn tại");
        }
    }
}
