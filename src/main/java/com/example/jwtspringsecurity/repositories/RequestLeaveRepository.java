package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.RequestLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLeaveRepository extends JpaRepository<RequestLeave, Long> {
    // Custom query methods can be added here
}