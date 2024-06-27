package com.example.jwtspringsecurity;


import com.example.jwtspringsecurity.enities.Role;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.enities.UserRole;
import com.example.jwtspringsecurity.repositories.UserRepo;
import com.example.jwtspringsecurity.services.jwt.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role role;
    private UserRole userRole;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("USER");

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");

        userRole = new UserRole();
        userRole.setId(1L);
        userRole.setUser(user);
        userRole.setRole(role);

        user.setUserRoles(List.of(userRole));
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("test@example.com");

        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());

        // In ra console các role
        userDetails.getAuthorities().forEach(authority -> {
            System.out.println("Role: " + authority.getAuthority());
        });

        verify(userRepo, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepo.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("unknown@example.com");
        });

        verify(userRepo, times(1)).findByEmail("unknown@example.com");
    }
}

