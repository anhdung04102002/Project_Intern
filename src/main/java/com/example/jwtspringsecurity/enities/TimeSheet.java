package com.example.jwtspringsecurity.enities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "timeSheet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String project;
    private String task;
    private String note;
    private int work_time;
    private Boolean type;
    private LocalDate date;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;
    private String status ;
}
