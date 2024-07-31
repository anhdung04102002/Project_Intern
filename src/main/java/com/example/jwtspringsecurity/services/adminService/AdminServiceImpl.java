package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.Mapper.UserMapper;
import com.example.jwtspringsecurity.dto.UserDTO;
import com.example.jwtspringsecurity.enities.*;
import com.example.jwtspringsecurity.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BranchRepo branchRepo;
    @Autowired
    private PositionRepo positionRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private UserMapper userMapper; // Inject UserMapper
    @Transactional
    @Override
    public User addUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO); // Map UserDTO to User

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Set Branch
        Long branchId = userDTO.getBranchId();
        if (branchId != null) {
            Branch branch = branchRepo.findById(branchId)
                    .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + branchId));
            user.setBranch(branch);
        }

        // Set Position
        Long positionId = userDTO.getPositionId();
        if (positionId != null) {
            Position position = positionRepo.findById(positionId)
                    .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + positionId));
            user.setPosition(position);
        }

        // Save user
        User savedUser = userRepo.save(user);

        // Assign role based on email
        assignRoleBasedOnEmail(savedUser);

        return savedUser;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }


    private void assignRoleBasedOnEmail(User user) {
        String email = user.getEmail();
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        Role assignedRole;

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

        if (email.endsWith("@nccadmin.asia")) {
            assignedRole = adminRole;
        } else if (email.endsWith("@nccpm.asia")) {
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
