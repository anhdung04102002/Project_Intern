package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
}
