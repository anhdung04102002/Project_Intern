package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepo extends JpaRepository<Branch, Long> {
    boolean existsByName(String name);
    Optional<Branch> findByName(String name);
    Page<Branch> findByDisplayName(String id, Pageable pageable);
}
