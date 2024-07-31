package com.example.jwtspringsecurity.services.UserService;

import com.example.jwtspringsecurity.Mapper.AttendanceMapper;
import com.example.jwtspringsecurity.dto.AttendanceDTO;
import com.example.jwtspringsecurity.dto.AttendanceRequestDto;
import com.example.jwtspringsecurity.dto.RegisterTimeDTO;
import com.example.jwtspringsecurity.enities.Attendance;
import com.example.jwtspringsecurity.enities.RegisterTime;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.repositories.AttendanceRepo;
import com.example.jwtspringsecurity.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AttendanceServiceImpl implements AttendanceService {
   @Autowired
   private AttendanceRepo attendanceRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RegisterTimeService registerTimeService;
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Transactional
    @Override
    public AttendanceDTO ProcessAttendanceToday(AttendanceRequestDto attendanceRequestDTO) {
        LocalDate today = LocalDate.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User currentUser = userRepo.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        Attendance todayAttendance = attendanceRepo.findByUserIdAndDate(currentUser.getId(), today)  // caái này nếu lấy ra cùng ngày sẽ không tạo mới mà sẽ lấy ra cái cũ
                .orElse(new Attendance());
        RegisterTimeDTO currentTimeDTO = registerTimeService.getCurrentTime();
        calculateAndApplyPunishment(currentTimeDTO, attendanceRequestDTO, todayAttendance);

        Attendance newAttendance = attendanceMapper.toAttendance(attendanceRequestDTO);


        todayAttendance.setUser(currentUser);
        todayAttendance.setDate(today);
        todayAttendance.setCheckIn(newAttendance.getCheckIn());
        todayAttendance.setCheckOut(newAttendance.getCheckOut());

        Attendance savedAttendance = attendanceRepo.save(todayAttendance);

        return attendanceMapper.ATTENDANCE_TO_ATTENDANCE_DTO(savedAttendance);
    }
    public void calculateAndApplyPunishment(RegisterTimeDTO currentTimeDTO, AttendanceRequestDto attendanceRequestDTO, Attendance todayAttendance) {
        LocalTime currentTimeDTOCheckIn = currentTimeDTO.getCheckIn();
        LocalTime currentTimeDTOCheckOut = currentTimeDTO.getCheckOut();
        LocalTime checkIn = attendanceRequestDTO.getCheckIn();
        LocalTime checkOut = attendanceRequestDTO.getCheckOut();

        int punishment = 0;
        int late = 0;
        int earlyLeave = 0;
        // xử lí trường hợp k checkin và checkout trước
        if(checkIn == null  || checkOut == null) {
            punishment += 30;
            if(checkIn == null && checkOut == null) {
                punishment += 20;
            }
        } else { // xử lí các trường hợp đến muộn
            if(Duration.between(currentTimeDTOCheckIn, checkIn).toMinutes() > 15) {
                punishment += 20;
                late = (int) Duration.between(currentTimeDTOCheckIn,checkIn ).toMinutes();
            }
            if(Duration.between(checkOut, currentTimeDTOCheckOut).toMinutes() > 15) {
                punishment += 20;
                earlyLeave = (int) Duration.between(checkOut, currentTimeDTOCheckOut).toMinutes();
            }
            todayAttendance.setCheckInLate(late);
            todayAttendance.setCheckOutEarly(earlyLeave);
            todayAttendance.setPunishmentMoney(punishment);
        }
    }
}
