package com.example.jwtspringsecurity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TimeSheetWeekDTO {
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private Double totalHours;
}
