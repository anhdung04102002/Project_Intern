package com.example.jwtspringsecurity.enities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "position")
@Data
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
}
