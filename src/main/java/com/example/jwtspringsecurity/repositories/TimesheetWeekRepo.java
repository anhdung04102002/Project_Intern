package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.TimeSheet;
import com.example.jwtspringsecurity.enities.TimesheetWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetWeekRepo extends JpaRepository<TimesheetWeek, Long> {
//    @Query("SELECT t FROM TimeSheet t WHERE t.user.id = :userId AND t.date >= :startDate AND t.date <= :endDate")
//    List<TimeSheet> findAllByUserAndDateBetween(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
