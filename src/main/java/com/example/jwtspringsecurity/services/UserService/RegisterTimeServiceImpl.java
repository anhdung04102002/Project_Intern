package com.example.jwtspringsecurity.services.UserService;

import com.example.jwtspringsecurity.Mapper.RegisterTimeMapper;
import com.example.jwtspringsecurity.dto.RegisterTimeDTO;
import com.example.jwtspringsecurity.enities.RegisterTime;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.repositories.RegisterTimeRepo;
import com.example.jwtspringsecurity.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RegisterTimeServiceImpl implements RegisterTimeService {
    @Autowired
    private RegisterTimeRepo registerTimeRepo;

    @Autowired
    private RegisterTimeMapper registerTimeMapper;
    @Autowired
    private UserRepo userRepo;

    @Transactional
    @Override
    public RegisterTimeDTO regisWorkingTime(RegisterTimeDTO registerTimeDTO) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User currentUser = userRepo.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        registerTimeDTO.setStatus("PENDING");
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = LocalDateTime.now().format(dateTimeFormat);
        LocalDateTime parsedTime = LocalDateTime.parse(formattedDate, dateTimeFormat);

        registerTimeDTO.setRequestTime(parsedTime);

        // Convert DTO to entity
        RegisterTime registerTime = registerTimeMapper.REGISTER_TIME_DTO_TO_REGISTER_TIMEs(registerTimeDTO);

        registerTime.setUser(currentUser); // Assuming RegisterTime has a field for the user ID and a setter method


        boolean timeExists = registerTimeRepo.findByUserIdAndDateAndCheckInAndCheckOut(
                currentUser.getId(), registerTime.getDate(), registerTime.getCheckIn(), registerTime.getCheckOut()
        ).isPresent();
        if (timeExists) {
            throw new IllegalStateException("Check-in và Check-out đã được đăng ký khung giờ này!"); //IllegalArgumentException xảy ra khi một phương thức nhận được đối số không hợp lệ hoặc không đúng kiểu.
        }
        registerTime = registerTimeRepo.save(registerTime);


        // Convert entity back to DTO
        return registerTimeMapper.REGISTER_TIME_TO_REGISTER_TIME_DTOs(registerTime);
    }

    @Override
    public RegisterTimeDTO getCurrentTime() { // logic là query ra thời gian cuối cùng đã được approve trước ngày hôm nay sau(nếu không có thì trả về default time) nếu có thì lấy cái gần nhất
        LocalDate today = LocalDate.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User currentUser = userRepo.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));


        List<RegisterTime> approvedTimes = registerTimeRepo.findByUserIdAndStatusAndDateBeforeOrderByDateDesc(currentUser.getId(),"APPROVE", today.plusDays(1)); // tìm tất cả các ngày trạng thái approve trước ngày hôm sau (tính cho hôm nay)
        // tìm trạng thái ngày được approve gần nhất
        if (!approvedTimes.isEmpty()) {
            RegisterTime latestApprovedTime = approvedTimes.get(0);
            return mapToDto(latestApprovedTime);
        }

        return getDefaultTime();
    }


    private RegisterTimeDTO getDefaultTime() {
        RegisterTimeDTO defaultTime = new RegisterTimeDTO();
        defaultTime.setDate(LocalDate.now());
        defaultTime.setCheckIn(LocalTime.of(8, 30));
        defaultTime.setCheckOut(LocalTime.of(17, 30));
        return defaultTime;
    }
    private RegisterTimeDTO mapToDto(RegisterTime registerTime) {
        RegisterTimeDTO dto = new RegisterTimeDTO();
        dto.setDate(registerTime.getDate());
        dto.setCheckIn(registerTime.getCheckIn());
        dto.setCheckOut(registerTime.getCheckOut());
        dto.setStatus(registerTime.getStatus());
        return dto;
    }
}

