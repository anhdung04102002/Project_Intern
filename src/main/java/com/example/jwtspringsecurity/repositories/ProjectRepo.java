package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    boolean existsByName(String name);
    Optional<Project> findByName(String name);
}
