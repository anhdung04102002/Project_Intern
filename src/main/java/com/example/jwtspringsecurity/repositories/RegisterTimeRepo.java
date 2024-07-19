package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.RegisterTime;
import com.example.jwtspringsecurity.enities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface RegisterTimeRepo extends JpaRepository<RegisterTime, Long> {
    @Query("SELECT r FROM RegisterTime r WHERE r.user.id = :userId  AND r.date = :date AND r.checkIn = :checkIn AND r.checkOut = :checkOut")
    Optional<RegisterTime> findByUserIdAndDateAndCheckInAndCheckOut(Long userId, LocalDate date, LocalTime checkIn, LocalTime checkOut);

    List<RegisterTime> findByUserIdAndStatusAndDateBeforeOrderByDateDesc(Long userId, String status, LocalDate date); // SẮP XẾP THEO THỨ TỰ giảm dần CỦA TRƯỜNG DATE
}
