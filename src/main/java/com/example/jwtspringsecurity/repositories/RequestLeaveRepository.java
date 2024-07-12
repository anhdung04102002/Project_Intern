package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.RequestLeave;
import com.example.jwtspringsecurity.enities.RequestType;
import com.example.jwtspringsecurity.enities.SubRequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestLeaveRepository extends JpaRepository<RequestLeave, Long> {

    //user
    @Query("SELECT r FROM RequestLeave r WHERE FUNCTION('YEAR', r.date) = :year AND FUNCTION('MONTH', r.date) = :month AND r.requestType = :requestType  AND r.user.id = :userId")
    Page<RequestLeave> findByUserAll(int year, int month, RequestType requestType,   Long userId, Pageable pageable);


    @Query("SELECT r FROM RequestLeave r WHERE FUNCTION('YEAR', r.date) = :year AND FUNCTION('MONTH', r.date) = :month  AND r.user.id = :userId")
    Page<RequestLeave> findByUserIfRequestTypeIsAll(int year ,int month,  Long userId, Pageable pageable);



    // manager
    @Query("SELECT r FROM RequestLeave r WHERE r.status = :status")
        // Custom query methods can be added here
    Page<RequestLeave> findByStatus(String status, Pageable pageable);
    @Query("SELECT r FROM RequestLeave r JOIN r.user u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :username, '%')) AND LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    Page<RequestLeave> findByUsernameAndEmail(String username, String email, Pageable pageable);
    Page<RequestLeave> findByRequestType(RequestType requestType, Pageable pageable);


    // tìm kiếm chuẩn và tổng hợp (manager)

    @Query("SELECT r FROM RequestLeave r WHERE FUNCTION('YEAR', r.date) = FUNCTION('YEAR', CURRENT_DATE) AND FUNCTION('MONTH', r.date) = :month AND r.requestType = :requestType AND r.subRequestType = :subRequestType AND r.status = :status")
    Page<RequestLeave> findByCurrentYearMonthAndTypesAndStatus(int month, RequestType requestType, SubRequestType subRequestType, String status, Pageable pageable);

    @Query("SELECT r FROM RequestLeave r WHERE FUNCTION('YEAR', r.date) = FUNCTION('YEAR', CURRENT_DATE) AND FUNCTION('MONTH', r.date) = :month AND r.status = :status")
    Page<RequestLeave> findByYearMonthAndStatus(int month, String status, Pageable pageable);

    @Query("SELECT r FROM RequestLeave r WHERE FUNCTION('YEAR', r.date) = FUNCTION('YEAR', CURRENT_DATE) AND FUNCTION('MONTH', r.date) = :month AND r.subRequestType = :subRequestType AND r.status = :status")
    Page<RequestLeave> findByYearMonthAndSubRequestTypeAndStatus(int month, SubRequestType subRequestType, String status, Pageable pageable);

    @Query("SELECT r FROM RequestLeave r WHERE FUNCTION('YEAR', r.date) = FUNCTION('YEAR', CURRENT_DATE) AND FUNCTION('MONTH', r.date) = :month AND r.requestType = :requestType AND r.status = :status")
    Page<RequestLeave> findByYearMonthAndRequestTypeAndStatus(int month, RequestType requestType, String status, Pageable pageable);
}