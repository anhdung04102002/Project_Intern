package com.example.jwtspringsecurity.enities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private LocalDate dob;
    private boolean status;
    private String userType;
    private String level;
    private boolean sex;
    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects;
}
