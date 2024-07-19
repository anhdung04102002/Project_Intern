package com.example.jwtspringsecurity.dto;

import com.example.jwtspringsecurity.enities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TimeSheetDTO {
    private String project;
    private String task;
    private String note;
    private int work_time;
    private Boolean type;
    private LocalDate date;
    private String status;
}
