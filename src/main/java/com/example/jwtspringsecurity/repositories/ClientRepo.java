package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.Client;
import com.example.jwtspringsecurity.enities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepo extends JpaRepository<Client, Long> {
    @Query("SELECT u FROM Client u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.code) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.address) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Client> search(String keyword, Pageable pageable);
}
