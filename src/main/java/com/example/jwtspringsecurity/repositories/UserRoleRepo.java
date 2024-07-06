package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.enities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);



}
