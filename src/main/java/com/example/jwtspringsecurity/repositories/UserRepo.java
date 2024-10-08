package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.Branch;
import com.example.jwtspringsecurity.enities.Project;
import com.example.jwtspringsecurity.enities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<User> search(String keyword, Pageable pageable);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    @Query("SELECT p FROM Project p JOIN p.users u WHERE u.id = :userId")
    List<Project> findAllProjectsByUserId(@Param("userId") Long userId);
    List<User> findByStatus(boolean status);
    Page<User> findByStatus(boolean status, Pageable pageable);
   // filter user theo branch
    Page<User> findByBranch(Branch branch, Pageable pageable);

}
