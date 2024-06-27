package com.example.jwtspringsecurity.controller.Admin;

import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.services.adminService.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/admin/user_add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody User user)
    {
        User newUser = adminService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
