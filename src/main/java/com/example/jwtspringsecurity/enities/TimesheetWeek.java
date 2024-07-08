package com.example.jwtspringsecurity.enities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private Double totalHours;
}
