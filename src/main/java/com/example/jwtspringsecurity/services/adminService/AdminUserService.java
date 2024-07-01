package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminUserService {
    List<User> getAllUser();
    void saveUser(User user);
    User getUser(int id);
    void deleteUser(int id);
    Page<User> getAllUserwithPage(int page, int size);

}
