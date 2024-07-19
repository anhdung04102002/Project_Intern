package com.example.jwtspringsecurity.services.UserService;

import com.example.jwtspringsecurity.dto.AttendanceDTO;
import com.example.jwtspringsecurity.dto.AttendanceRequestDto;

public interface AttendanceService {
    AttendanceDTO ProcessAttendanceToday(AttendanceRequestDto attendanceRequestDTO);
}
