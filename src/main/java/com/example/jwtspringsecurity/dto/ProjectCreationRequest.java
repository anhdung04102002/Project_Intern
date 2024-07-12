package com.example.jwtspringsecurity.dto;

import lombok.Data;

import java.util.List;
@Data
public class ProjectCreationRequest {
    private String name;
    private List<Long> userIds;
    private List<String> taskNames;

}
