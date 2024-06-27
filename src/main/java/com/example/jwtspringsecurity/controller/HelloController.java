package com.example.jwtspringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/user/hello")
    @PreAuthorize("hasRole('USER')")
    public HelloResponse hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // In ra các quyền của người dùng hiện tại
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            System.out.println("Current Authority: " + authority.getAuthority());
        }
        return  new HelloResponse("Hello Hoang Anh Dung");
    }

    @GetMapping("/admin/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public HelloResponse helloadmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // In ra các quyền của người dùng hiện tại
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            System.out.println("Current Authority: " + authority.getAuthority());
        }
        return  new HelloResponse("Hello Hoang Anh Dung");
    }
}
