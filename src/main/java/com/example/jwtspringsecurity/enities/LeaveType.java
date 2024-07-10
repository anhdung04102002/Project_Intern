package com.example.jwtspringsecurity.enities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "leave_type")
@Data
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int days;
}
