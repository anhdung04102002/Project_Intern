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
            String auHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            if(auHeader != null && auHeader.startsWith("Bearer ")){
                token = auHeader.substring(7);
                username = jwtUtil.extractUsername(token);
            }
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){ // kiểm tra ngữ cảnh không có người dùng nào được xác thực
                var userDetails = UserService.loadUserByUsername(username);
                if(jwtUtil.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
            filterChain.doFilter(request, response);
    }
}
