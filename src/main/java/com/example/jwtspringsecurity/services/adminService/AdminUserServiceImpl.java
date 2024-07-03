package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.dto.BranchNotFoundException;
import com.example.jwtspringsecurity.dto.UserNotFoundException;
import com.example.jwtspringsecurity.enities.Branch;
import com.example.jwtspringsecurity.enities.Position;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.repositories.BranchRepo;
import com.example.jwtspringsecurity.repositories.PositionRepo;
import com.example.jwtspringsecurity.repositories.UserRepo;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    PositionRepo positionRepo;
    @Autowired
    BranchRepo branchRepo;
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
    public void deleteUser(long id) {
            this.userRepo.deleteById(id);
    }

    @Override
    public Page<User> getAllUserwithPage(int page, int size) {
        Page<User> userPage = userRepo.findAll(PageRequest.of(page - 1, size)); // Adjusted page to start from 0
        return userPage;
    }

    @Override
    public User getById(Long id) {
        return userRepo.findById(id).orElse(null);

    }

    @Override
    public User updateUser(User updatedUser) {
        // Ensure user exists before updating
        Long userId = updatedUser.getId();
        User existingUser = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
//            existingUser.setPassword(updatedUser.getPhone());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setDob(updatedUser.getDob());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setStatus(updatedUser.isStatus());
            existingUser.setUserType(updatedUser.getUserType());
            existingUser.setSalary(updatedUser.getSalary());
            existingUser.setSalaryDate(updatedUser.getSalaryDate());
            existingUser.setLevel(updatedUser.getLevel());
            existingUser.setSex(updatedUser.isSex());
//            existingUser.setPosition(updatedUser.getPosition());
//            existingUser.setBranch(updatedUser.getBranch());
            Position updatedPosition = updatedUser.getPosition();
            if (updatedPosition != null) {
                Position exitingPosition = positionRepo.findById(updatedPosition.getId()).orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + updatedPosition.getId()));
                existingUser.setPosition(exitingPosition);
            }

            // Check if branch exists in the database before setting
            Branch updatedBranch = updatedUser.getBranch();
            if (updatedBranch != null) {
                Branch existingBranch = branchRepo.findById(updatedBranch.getId()).orElseThrow(() -> new BranchNotFoundException("Branch not found with id: " + updatedBranch.getId()));
                existingUser.setBranch(existingBranch);
            }
            return userRepo.save(existingUser);
        } else {
            // Handle case where user does not exist
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    @Override
    public List<User> findByStatus(boolean status) {
        return userRepo.findByStatus(status);
    }

    @Override
    public Page<User> getAllUserWithPageAndStatus(Boolean status, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // page - 1 vì PageRequest đánh số trang từ 0
        if (status != null) {
            return userRepo.findByStatus(status, pageable);
        } else {
            return userRepo.findAll(pageable);
        }
    }

    @Override
    public Page<User> search(String keyword, int page, int size) {
        return userRepo.search(keyword, PageRequest.of(page - 1, size));
    }


}

