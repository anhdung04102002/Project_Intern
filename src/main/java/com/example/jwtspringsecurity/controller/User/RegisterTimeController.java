package com.example.jwtspringsecurity.controller.User;

import com.example.jwtspringsecurity.dto.RegisterTimeDTO;
import com.example.jwtspringsecurity.services.UserService.RegisterTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/register-time")
public class RegisterTimeController {
    @Autowired
    private RegisterTimeService registerTimeService;
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<?> registerWorkingTime(@RequestBody RegisterTimeDTO registerTimeDTO) {
        try {
            RegisterTimeDTO savedRegisterTimeDTO = registerTimeService.regisWorkingTime(registerTimeDTO);
            return ResponseEntity.ok(savedRegisterTimeDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("Không tồn tại user với email: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while registering working time: " + e.getMessage());
        }
    }
    @GetMapping("/current")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> getCurrentTime() {
        try {
            RegisterTimeDTO currentTimeDTO = registerTimeService.getCurrentTime();
            return ResponseEntity.ok(currentTimeDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while fetching current time: " + e.getMessage());
        }
    }
}
