package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.Mapper.UserMapper;
import com.example.jwtspringsecurity.controller.Admin.PasswordGenerator;
import com.example.jwtspringsecurity.dto.BranchNotFoundException;
import com.example.jwtspringsecurity.dto.UserDTO;
import com.example.jwtspringsecurity.dto.UserNotFoundException;
import com.example.jwtspringsecurity.enities.*;
import com.example.jwtspringsecurity.repositories.*;
import com.example.jwtspringsecurity.services.AuthServiceImpl;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    PositionRepo positionRepo;
    @Autowired
    BranchRepo branchRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

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

    @Transactional
    @Override
    public User updateUser(UserDTO updatedUserDTO) {
        // Ensure user exists before updating
        Long userId = updatedUserDTO.getId();
        User existingUser = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        // Xóa tất cả UserRole cũ
        User updatedUser = userMapper.userDTOToUser(updatedUserDTO);

        List<UserRole> userRoles = userRoleRepo.findByUser(existingUser);
        userRoleRepo.deleteAll(userRoles);

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
//            Position updatedPosition = updatedUser.getPosition();
//            if (updatedPosition != null) {
//                Position exitingPosition = positionRepo.findById(updatedPosition.getId()).orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + updatedPosition.getId()));
//                existingUser.setPosition(exitingPosition);
//            }

            // Check if branch exists in the database before setting
            if (updatedUserDTO.getBranchId() != null) {
                Branch branch = branchRepo.findById(updatedUserDTO.getBranchId())
                        .orElseThrow(() -> new BranchNotFoundException("Branch not found with id: " + updatedUserDTO.getBranchId()));
                existingUser.setBranch(branch);
            }


            assignRole(existingUser, updatedUserDTO.getEmail());
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

    @Override
    public Page<User> getUserWithPageAndBranch(Long branchId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (branchId == null) {
            return userRepo.findAll(pageable);
        } else {
            Branch branch = branchRepo.findById(branchId).orElseThrow(() -> new BranchNotFoundException("Branch not found with id: " + branchId));
            return userRepo.findByBranch(branch, pageable);
        }
    }

    @Override
    public Boolean deativeUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Không có user với id " + userId));
        user.setStatus(false);
        userRepo.save(user);
        return true;
    }

    @Override
    public Boolean activeUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Không có user với id " + userId));
        user.setStatus(true);
        userRepo.save(user);
        return true;
    }

    @Override
    public Boolean resetPassword(Long id,String newPassword) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Không có user với id " + id));
        if (user != null) {
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            userRepo.save(user);
            return true;
        }
        return false;
    }


    public void assignRole(User user, String email) {
        // xóa quyền  hiện tại để tránh tạo  nhiều role cho 1 user

        Role userRole = roleRepo.findByName("ROLE_USER").orElseGet(() -> {
            Role newUserRole = new Role();
            newUserRole.setName("ROLE_USER");
            roleRepo.save(newUserRole);
            return newUserRole;
        });
        Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> {
            Role newAdminRole = new Role();
            newAdminRole.setName("ROLE_ADMIN");
            roleRepo.save(newAdminRole);
            return newAdminRole;
        });
        Role managerRole = roleRepo.findByName("ROLE_MANAGER").orElseGet(() -> {
            Role newManagerRole = new Role();
            newManagerRole.setName("ROLE_MANAGER");
            roleRepo.save(newManagerRole);
            return newManagerRole;
        });

        Role assignedRole;

        if (email.endsWith("@nccadmin.asia")) {  // vai trò admin
            assignedRole = adminRole;
        } else if (email.endsWith("@nccpm.asia")) { // vai  trò quản lí
            assignedRole = managerRole;
        } else {
            assignedRole = userRole;
        }

        UserRole userRoleEntity = new UserRole();
        userRoleEntity.setUser(user);
        userRoleEntity.setRole(assignedRole);
        userRoleRepo.save(userRoleEntity);
    }
}

