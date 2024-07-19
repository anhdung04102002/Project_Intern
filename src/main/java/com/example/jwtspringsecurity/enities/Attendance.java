package com.example.jwtspringsecurity.enities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private int checkInLate;
    private int checkOutEarly;
    private int punishmentMoney;
    private String complain; // by user
    private String complainReply; // by manager
}
