package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.LeaveType;
import com.example.jwtspringsecurity.enities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Repository
public interface AdminUserService {
    List<User> getAllUser();
    void saveUser(User user);
    User getUser(int id);
    void deleteUser(long id);
    Page<User> getAllUserwithPage(int page, int size);
    User getById(Long id);
    User updateUser(User user);
    List<User> findByStatus(boolean status);
    Page<User> getAllUserWithPageAndStatus(Boolean status, int page, int size);
    Page<User> search(String keyword, int page, int size);
    Page<User> getUserWithPageAndBranch(Long branchId, int page, int size);
    Boolean deativeUser(Long id);
    Boolean activeUser(Long id);
    Boolean resetPassword(Long id,String encryptedPassword);
}
