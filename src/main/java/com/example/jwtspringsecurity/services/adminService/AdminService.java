package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.dto.UserDTO;
import com.example.jwtspringsecurity.enities.User;

public interface AdminService {
    User addUser(UserDTO userDTO);
    Boolean existsByEmail(String email);
}
