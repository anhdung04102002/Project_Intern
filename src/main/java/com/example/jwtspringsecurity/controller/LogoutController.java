package com.example.jwtspringsecurity.controller;

import com.example.jwtspringsecurity.utils.JwtTokenUtil;
import com.example.jwtspringsecurity.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LogoutController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = jwtTokenUtil.getTokenFromRequest(request);
        jwtTokenUtil.invalidateToken(token);
        return ResponseEntity.ok("Logged out successfully");
    }
}
