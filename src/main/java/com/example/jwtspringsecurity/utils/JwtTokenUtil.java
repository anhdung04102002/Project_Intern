package com.example.jwtspringsecurity.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtTokenUtil {

    private List<String> invalidatedTokens = new ArrayList<>();

    // Lấy token từ yêu cầu HTTP
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Vô hiệu hóa token
    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    // Kiểm tra xem token có bị vô hiệu hóa không
    public boolean isTokenInvalidated(String token) {
        return invalidatedTokens.contains(token);
    }
}
