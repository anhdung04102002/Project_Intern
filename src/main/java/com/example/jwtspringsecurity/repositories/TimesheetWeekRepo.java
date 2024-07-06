package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.TimesheetWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetWeekRepo extends JpaRepository<TimesheetWeek, Long> {
}
