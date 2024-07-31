package com.example.jwtspringsecurity.service;

import com.example.jwtspringsecurity.Mapper.AttendanceMapper;
import com.example.jwtspringsecurity.dto.AttendanceDTO;
import com.example.jwtspringsecurity.dto.AttendanceRequestDto;
import com.example.jwtspringsecurity.dto.RegisterTimeDTO;
import com.example.jwtspringsecurity.enities.Attendance;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.repositories.AttendanceRepo;
import com.example.jwtspringsecurity.repositories.UserRepo;
import com.example.jwtspringsecurity.services.UserService.AttendanceServiceImpl;
import com.example.jwtspringsecurity.services.UserService.RegisterTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AttendanceServiceImplTest {

    @Mock
    private AttendanceRepo attendanceRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private RegisterTimeService registerTimeService;

    @Mock
    private AttendanceMapper attendanceMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testProcessAttendanceToday_UserNotFound() {
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepo.findByEmail(eq("test@example.com"))).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            attendanceService.ProcessAttendanceToday(new AttendanceRequestDto());
        });
    }

    @Test
    void testProcessAttendanceToday_AttendanceExists() {
        User user = new User();
        user.setId(1L);
        Attendance existingAttendance = new Attendance();
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepo.findByEmail(eq("test@example.com"))).thenReturn(Optional.of(user));
        when(attendanceRepo.findByUserIdAndDate(eq(1L), any(LocalDate.class))).thenReturn(Optional.of(existingAttendance));
        when(registerTimeService.getCurrentTime()).thenReturn(new RegisterTimeDTO());
        when(attendanceMapper.toAttendance(any(AttendanceRequestDto.class))).thenReturn(new Attendance());
        when(attendanceRepo.save(any(Attendance.class))).thenReturn(existingAttendance);
        when(attendanceMapper.ATTENDANCE_TO_ATTENDANCE_DTO(any(Attendance.class))).thenReturn(new AttendanceDTO());

        AttendanceDTO result = attendanceService.ProcessAttendanceToday(new AttendanceRequestDto());

        assertNotNull(result);
        verify(attendanceRepo, times(0)).save(any(Attendance.class)); // Should not save as attendance already exists
    }

    @Test
    void testProcessAttendanceToday_NewAttendance() {
        User user = new User();
        user.setId(1L);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepo.findByEmail(eq("test@example.com"))).thenReturn(Optional.of(user));
        when(attendanceRepo.findByUserIdAndDate(eq(1L), any(LocalDate.class))).thenReturn(Optional.empty());
        when(registerTimeService.getCurrentTime()).thenReturn(new RegisterTimeDTO());
        when(attendanceMapper.toAttendance(any(AttendanceRequestDto.class))).thenReturn(new Attendance());
        when(attendanceRepo.save(any(Attendance.class))).thenReturn(new Attendance());
        when(attendanceMapper.ATTENDANCE_TO_ATTENDANCE_DTO(any(Attendance.class))).thenReturn(new AttendanceDTO());

        AttendanceDTO result = attendanceService.ProcessAttendanceToday(new AttendanceRequestDto());

        assertNotNull(result);
        verify(attendanceRepo, times(1)).save(any(Attendance.class)); // Should save new attendance
    }

    @Test
    void testCalculateAndApplyPunishment() {
        RegisterTimeDTO currentTimeDTO = new RegisterTimeDTO();
        currentTimeDTO.setCheckIn(LocalTime.of(8, 30));
        currentTimeDTO.setCheckOut(LocalTime.of(17, 30));
        AttendanceRequestDto attendanceRequestDTO = new AttendanceRequestDto();
        attendanceRequestDTO.setCheckIn(LocalTime.of(8, 46));
        attendanceRequestDTO.setCheckOut(LocalTime.of(17, 40));
        Attendance todayAttendance = new Attendance();

        attendanceService.calculateAndApplyPunishment(currentTimeDTO, attendanceRequestDTO, todayAttendance);

        assertEquals(20, todayAttendance.getPunishmentMoney());
        assertEquals(16, todayAttendance.getCheckInLate());

    }
}
