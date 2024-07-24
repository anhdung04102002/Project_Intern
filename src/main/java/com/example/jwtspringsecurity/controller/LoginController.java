package com.example.jwtspringsecurity.controller;

import com.example.jwtspringsecurity.dto.LoginReponse;
import com.example.jwtspringsecurity.dto.LoginRequest;
import com.example.jwtspringsecurity.services.adminService.EmailService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")

public class LoginController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager  authenticationManager; // phải được cấu hình  webSecurityConfiguration
    @Autowired
    private UserServiceImpl UserService;



    @PostMapping
//    @Transactional
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(   //  xác thực thông tin so  với  chi tiết người dùng (tiêm cái loadUserByUsername vào đây)
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Sai tài khoản hoặc mật khẩu"));
        }
        UserDetails userDetails;
        try {
            userDetails = UserService.loadUserByUsername(loginRequest.getEmail());
        } catch (UsernameNotFoundException e) {
            if (e.getMessage().contains("vô hiệu hóa")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Tài khoản này đã bị vô hiệu hóa."));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Sai tài khoản hoặc mật khẩu"));
        }
        // Lấy danh sách vai trò của người dùng
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        String roles = String.join(",", rolesList); // nối list thành chuỗi với dấu phẩy, nếu chỉ có 1 phâm tử thì không có dấu phẩy
//        System.out.println("User Role: " + roles);
        // In ra console các quyền của người dùng sau khi đăng nhập thành công
        userDetails.getAuthorities().forEach(authority -> {
            System.out.println("User Role: " + authority.getAuthority());
        });
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        // logic có thể được thêm vào đây
        return ResponseEntity.ok(new LoginReponse(jwt,refreshToken,roles));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            if (refreshToken != null) {
//                refreshToken = refreshToken.replace("\"", "").trim(); // Loại bỏ dấu ngoặc kép và khoảng trắng

                // Validate và extract thông tin từ refresh token
                String username = jwtUtil.extractUsername(refreshToken, true);
                UserDetails userDetails = UserService.loadUserByUsername(username);

                if (jwtUtil.validateToken(refreshToken, userDetails, true)) {
                    String newJwt = jwtUtil.generateToken(userDetails.getUsername());
                    String newRefreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

                    List<String> rolesList = userDetails.getAuthorities().stream()
                            .map(authority -> authority.getAuthority())
                            .collect(Collectors.toList());
                    String roles = String.join(",", rolesList);

                    return ResponseEntity.ok(new LoginReponse(newJwt, newRefreshToken, roles));
                } else {
                    System.out.println("Token validation failed");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else {
                System.out.println("Refresh token is missing");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (UsernameNotFoundException e) {
            System.out.println("User not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
