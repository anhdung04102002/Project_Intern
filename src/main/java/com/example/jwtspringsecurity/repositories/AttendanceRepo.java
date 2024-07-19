package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.Attendance;
import com.example.jwtspringsecurity.enities.RegisterTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserIdAndDate(Long userId, LocalDate date);
}
