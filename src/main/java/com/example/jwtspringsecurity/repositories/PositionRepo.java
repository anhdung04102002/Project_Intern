package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.Branch;
import com.example.jwtspringsecurity.enities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepo  extends JpaRepository<Position, Long> {
}
