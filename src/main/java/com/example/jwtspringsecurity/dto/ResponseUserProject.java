package com.example.jwtspringsecurity.dto;

import lombok.Data;

@Data
public class ResponseUserProject {
    private String name;
    private String email;
    private String address;
    private String phone;
    private String level;
    private boolean sex;
    private String userType;
}
