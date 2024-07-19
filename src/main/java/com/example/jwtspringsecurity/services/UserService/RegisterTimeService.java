package com.example.jwtspringsecurity.services.UserService;

import com.example.jwtspringsecurity.dto.RegisterTimeDTO;
import com.example.jwtspringsecurity.enities.RegisterTime;

public interface RegisterTimeService {
    RegisterTimeDTO regisWorkingTime(RegisterTimeDTO registerTimeDTO);
    RegisterTimeDTO getCurrentTime();

}
