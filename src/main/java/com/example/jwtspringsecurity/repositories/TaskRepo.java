package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
