package com.example.jwtspringsecurity.controller.Admin;

import com.example.jwtspringsecurity.dto.ApiResponse;
import com.example.jwtspringsecurity.dto.BranchNotFoundException;
import com.example.jwtspringsecurity.dto.UserDTO;
import com.example.jwtspringsecurity.dto.UserNotFoundException;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.services.adminService.AdminService;
import com.example.jwtspringsecurity.services.adminService.AdminUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user_add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        if (adminService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email already exists");
        }
        User newUser = adminService.addUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/user_getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUser() // interface Iterable<T> extends Collection<T>  duyệt qua tất cả phần tử của collection
    {
        List<User> users = adminUserService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/user_pagination")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<User>>> getAllUserWithPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<User> userPage = adminUserService.getAllUserwithPage(page, size);
            List<User> user = userPage.getContent();
            return ResponseEntity.ok(new ApiResponse<>(userPage.getTotalPages(), user));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/user_getById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = adminUserService.getById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Controller layer
    @PutMapping("/user_update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser) {
        try {
            User savedUser = adminUserService.updateUser(updatedUser);
            return ResponseEntity.ok(savedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "User not found: " + e.getMessage()));
        } catch (BranchNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Branch not found: " + e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Internal server error"));
        }
    }

    @DeleteMapping("/user_delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        try {
            adminUserService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }

    @GetMapping("/user_filterStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<User>>> filterUserByStatus(
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<User> userPage;
            if (status == null) {
                userPage = adminUserService.getAllUserWithPageAndStatus(true, page, size); // Lấy tất cả nếu status không được chỉ định
            } else {
                userPage = adminUserService.getAllUserWithPageAndStatus(status, page, size);
            }
            List<User> users = userPage.getContent();
            return ResponseEntity.ok(new ApiResponse<>(userPage.getTotalPages(), users));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user_filterBranch")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<User>>> filterUserByBranch(
            @RequestParam(required = false) Long branchId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<User> userPage = adminUserService.getUserWithPageAndBranch(branchId, page, size);
            List<User> users = userPage.getContent();
            return ResponseEntity.ok(new ApiResponse<>(userPage.getTotalPages(), users));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user_search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> searchUser(   // để ? để trả về bất kỳ kiểu dữ liệu nào, đây là dạng generic
                                           @RequestParam(required = false) String name,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<User> userPage;
            if (name != null && !name.isEmpty()) {
                userPage = adminUserService.search(name, page, size);
            } else {
                userPage = adminUserService.getAllUserwithPage(page, size);
            }
            List<User> users = userPage.getContent();
            if (users.isEmpty()) {
                return ResponseEntity.ok("No users found"); // Return string message
            } else
                return ResponseEntity.ok(new ApiResponse<>(userPage.getTotalPages(), users));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user_deactivate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        try {
            boolean result = adminUserService.deativeUser(id);
            if (result) {
                return ResponseEntity.ok().body("User deactivated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to deactivate user");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Internal server error"));
        }
    }

    @PutMapping("/user_active/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activeUser(@PathVariable Long id) {
        try {
            boolean result = adminUserService.activeUser(id);
            if (result) {
                return ResponseEntity.ok().body("User active successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to active user");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Internal server error"));
        }
    }

    @GetMapping("/generate-password")
    public ResponseEntity<String> generatePassword(@RequestParam(defaultValue = "5") int length) {
        String password = PasswordGenerator.generateRandomPassword(length);
        return ResponseEntity.ok(password);
    }

        @PutMapping("/reset-password/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String newPassword = payload.get("password");
        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }
        Boolean result = adminUserService.resetPassword(id, newPassword);
        if (result) {
            return ResponseEntity.ok().body("Password updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update password");
        }

    }
}
