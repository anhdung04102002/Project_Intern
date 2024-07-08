package com.example.jwtspringsecurity.controller;

import com.example.jwtspringsecurity.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api")
//public class LogoutController {
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request) {
//        // Thông báo cho client xóa JWT khỏi bộ nhớ, từ đó "đăng xuất"
//        String token = (request);
//        return ResponseEntity.ok("Đăng xuất thành công. Vui lòng xóa token trên phía client.");
//    }
//}
