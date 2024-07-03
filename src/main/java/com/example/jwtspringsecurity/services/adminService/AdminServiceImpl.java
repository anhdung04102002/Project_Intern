package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.*;
import com.example.jwtspringsecurity.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
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

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set Branch
        Long branchId = user.getBranch() != null ? user.getBranch().getId() : null;
        if (branchId != null) {
            Branch branch = branchRepo.findById(branchId)
                    .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + branchId));
            user.setBranch(branch);
        }

        // Set Position
        Long positionId = user.getPosition() != null ? user.getPosition().getId() : null;
        if (positionId != null) {
            Position position = positionRepo.findById(positionId)
                    .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + positionId));
            user.setPosition(position);
        }

        // Set Projects
//        List<Long> projectIds = user.getProjects().stream()
//                .map(Project::getId)
//                .collect(Collectors.toList());
//        if (!projectIds.isEmpty()) {
//            List<Project> projects = projectIds.stream()
//                    .map(projectId -> projectRepo.findById(projectId)
//                            .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId)))
//                    .collect(Collectors.toList());
//            user.setProjects(projects);
//        }

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
