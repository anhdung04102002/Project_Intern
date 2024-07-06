package com.example.jwtspringsecurity.enities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "timeSheetWeek")
@Data
public class TimesheetWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private Double totalHours;
}
