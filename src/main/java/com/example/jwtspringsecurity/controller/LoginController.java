package com.example.jwtspringsecurity.controller;

import com.example.jwtspringsecurity.dto.LoginReponse;
import com.example.jwtspringsecurity.dto.LoginRequest;
import com.example.jwtspringsecurity.services.jwt.UserServiceImpl;
import com.example.jwtspringsecurity.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")

public class LoginController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager; // phải được cấu hình  webSecurityConfiguration
    @Autowired
    private UserServiceImpl UserService;




    @PostMapping
    public ResponseEntity<LoginReponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(   //  xác thực thông tin so  với  chi tiết người dùng
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails;
        try {
            userDetails = UserService.loadUserByUsername(loginRequest.getEmail());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // In ra console các quyền của người dùng sau khi đăng nhập thành công
        userDetails.getAuthorities().forEach(authority -> {
            System.out.println("User Role: " + authority.getAuthority());
        });

        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        // logic có thể được thêm vào đây
        return ResponseEntity.ok(new LoginReponse(jwt));
    }

}
