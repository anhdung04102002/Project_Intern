package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.TimeSheet;
import com.example.jwtspringsecurity.enities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeSheetRepo extends JpaRepository<TimeSheet, Long> {
    TimeSheet findByDate(LocalDate date);
    TimeSheet findByDateAndUser(LocalDate date, User user);
    @Query("SELECT t FROM TimeSheet t WHERE t.user.id = :userId AND t.date >= :startDate AND t.date <= :endDate")
    List<TimeSheet> findAllByUserAndDateBetween(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
