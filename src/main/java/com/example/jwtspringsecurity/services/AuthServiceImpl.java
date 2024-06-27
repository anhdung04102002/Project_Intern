package com.example.jwtspringsecurity.services;

import com.example.jwtspringsecurity.dto.SignUpRequest;
import com.example.jwtspringsecurity.enities.Role;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.enities.UserRole;
import com.example.jwtspringsecurity.repositories.RoleRepo;
import com.example.jwtspringsecurity.repositories.UserRepo;
import com.example.jwtspringsecurity.repositories.UserRoleRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private RoleRepo roleRepo;
    private UserRepo UserRepo;
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public AuthServiceImpl(UserRepo UserRepo, BCryptPasswordEncoder passwordEncoder) {
        this.UserRepo = UserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean createUser(SignUpRequest signUpRequest) {
        // CHECK IF user ALREADY EXISTS
        if(UserRepo.existsByEmail(signUpRequest.getEmail())){
            return false;
        }
        User user = new User();

        BeanUtils.copyProperties(signUpRequest, user); // copy các thuộc tính từ signUpRequest sang User(phải trùng nhau)
        String hassedPass = passwordEncoder.encode(signUpRequest.getPassword());
        user.setPassword(hassedPass);
        UserRepo.save(user);

        // tạo các user
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
        // xét email
        String email = user.getEmail();

        Role assignedRole;

        if (email.endsWith("@nccadmin.asia")) {  // vai trò admin
            assignedRole = adminRole;
        } else if (email.endsWith("@nccpm.asia")) { // vai  trò quản lí
            assignedRole = managerRole;
        } else {
            assignedRole = userRole;
        }

        // tạo quyền cho user( trong user không có quyền mà quyền phải đặt trong user_role để dễ quản lí)

        UserRole userRoleEntity = new UserRole();
        userRoleEntity.setUser(user);
        userRoleEntity.setRole(assignedRole);
        userRoleRepo.save(userRoleEntity);

        return true;
    }
}
