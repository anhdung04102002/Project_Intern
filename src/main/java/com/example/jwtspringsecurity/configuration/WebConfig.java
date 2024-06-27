package com.example.jwtspringsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
// file này tạo ra để cấu hình CORS cho ứng dụng Spring Boot(đáp ứng vơi frontend Angular)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Áp dụng cho tất cả các endpoint
                .allowedOrigins("http://127.0.0.1:5500")  // Cho phép từ nguồn này
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Các phương thức được phép
                .allowedHeaders("*")  // Cho phép tất cả các header
                .allowCredentials(true);  // Cho phép gửi thông tin xác thực như cookies
    }
}
