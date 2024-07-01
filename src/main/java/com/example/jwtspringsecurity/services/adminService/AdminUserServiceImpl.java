package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public Page<User> getAllUserwithPage(int page, int size) {
        Page<User> userPage = userRepo.findAll(PageRequest.of(page - 1, size)); // Adjusted page to start from 0
        return userPage;
    }
}
