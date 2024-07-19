package com.example.jwtspringsecurity.controller.User;

import com.example.jwtspringsecurity.dto.AttendanceDTO;
import com.example.jwtspringsecurity.dto.AttendanceRequestDto;
import com.example.jwtspringsecurity.services.UserService.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @PostMapping("/process")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> processAttendanceToday(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        try {
            AttendanceDTO attendanceDTO = attendanceService.ProcessAttendanceToday(attendanceRequestDto);
            return ResponseEntity.ok(attendanceDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process attendance: " + e.getMessage());
        }
    }
}
