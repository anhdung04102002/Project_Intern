package com.example.jwtspringsecurity.filters;

import com.example.jwtspringsecurity.services.jwt.UserServiceImpl;
import com.example.jwtspringsecurity.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter { //bộ lọc thực thi 1 lần mỗi yêu cầu
    @Autowired
    private UserServiceImpl UserService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            try {
                // Giải mã token sử dụng SECRET_KEY (token chính)
                username = jwtUtil.extractUsername(token, false);
            } catch (Exception e) {
                // Ignore nếu không giải mã được
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = UserService.loadUserByUsername(username);
            boolean isTokenValid = false;

            try {
                // Kiểm tra token với SECRET_KEY
                isTokenValid = jwtUtil.validateToken(token, userDetails, false);
            } catch (Exception ignored) {
                // Ignore nếu không validate được
            }

            if (isTokenValid) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
