package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.RequestLeave;
import com.example.jwtspringsecurity.enities.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestLeaveRepository extends JpaRepository<RequestLeave, Long> {
    @Query("SELECT r FROM RequestLeave r WHERE r.status = :status")
        // Custom query methods can be added here
    Page<RequestLeave> findByStatus(String status, Pageable pageable);
    @Query("SELECT r FROM RequestLeave r JOIN r.user u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :username, '%')) AND LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    Page<RequestLeave> findByUsernameAndEmail(String username, String email, Pageable pageable);
    Page<RequestLeave> findByRequestType(RequestType requestType, Pageable pageable);

}