package com.example.jwtspringsecurity.enities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "public_holiday")
@Data
public class PublicHoliday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
