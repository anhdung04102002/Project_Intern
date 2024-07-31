package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.Project;
import com.example.jwtspringsecurity.enities.Task;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    boolean existsByName(String name);
    Optional<Project> findByName(String name);
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId")
//    @Cacheable("projectsCache")
    List<Task> findTasksByProjectId(@Param("projectId") Long projectId);
}
