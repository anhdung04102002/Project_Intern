package com.example.jwtspringsecurity.enities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_role")
@Data
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}