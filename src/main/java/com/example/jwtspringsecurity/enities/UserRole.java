package com.example.jwtspringsecurity.enities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference("user-role") // phải có name k bị lỗi 415
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference("role-user")
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