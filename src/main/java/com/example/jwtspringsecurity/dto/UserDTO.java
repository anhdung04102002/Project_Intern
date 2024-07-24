package com.example.jwtspringsecurity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String password;
    private String email;
    private LocalDate dob;
    private boolean status;
    private String userType;
    private BigDecimal salary;
    private LocalDate salaryDate;
    private String address;
    private String phone;
    private String level;
    private boolean sex;
    private Long branchId; // Assuming you need to pass ID for branch
    private Long positionId;
}
