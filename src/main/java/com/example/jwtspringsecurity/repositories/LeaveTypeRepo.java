package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepo extends JpaRepository<LeaveType, Long>{
}
