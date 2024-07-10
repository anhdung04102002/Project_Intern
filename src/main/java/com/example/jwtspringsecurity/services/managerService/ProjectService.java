package com.example.jwtspringsecurity.services.managerService;

import com.example.jwtspringsecurity.enities.Project;
import com.example.jwtspringsecurity.enities.Task;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjectsByUser(Long userId);
    List<Task> getTasksByProjectId(Long projectId);
}
