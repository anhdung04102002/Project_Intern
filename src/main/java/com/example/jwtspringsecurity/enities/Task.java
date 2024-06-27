package com.example.jwtspringsecurity.enities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
