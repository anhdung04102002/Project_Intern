package com.example.jwtspringsecurity.enities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "timeSheet")
@Data
public class TimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String project;
    private String task;
    private String note;
    private int work_time;
    private Boolean type;
    private String date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String status = "new";
}
