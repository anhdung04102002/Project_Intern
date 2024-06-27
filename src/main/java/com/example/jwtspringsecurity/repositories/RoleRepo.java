package com.example.jwtspringsecurity.repositories;

import com.example.jwtspringsecurity.enities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
//    Role findByName(String name);
    Optional<Role> findByName(String name);
}
