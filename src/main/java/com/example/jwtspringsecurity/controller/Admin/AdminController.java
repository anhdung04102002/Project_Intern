package com.example.jwtspringsecurity.controller.Admin;

import com.example.jwtspringsecurity.dto.ApiResponse;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.services.adminService.AdminService;
import com.example.jwtspringsecurity.services.adminService.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/user_add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody User user)
    {
        User newUser = adminService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/user_getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUser() // interface Iterable<T> extends Collection<T>  duyệt qua tất cả phần tử của collection
    {
        List<User> users = adminUserService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/user_pagination/{page}/{size}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<User>>> getAllUserWithPage(@PathVariable int page, @PathVariable int size) {
        try {
            Page<User> userPage = adminUserService.getAllUserwithPage(page, size);
            List<User> user = userPage.getContent();
            return ResponseEntity.ok(new ApiResponse<>(userPage.getTotalPages(), user));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
