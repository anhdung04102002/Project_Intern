package com.example.jwtspringsecurity.services.managerService;

import com.example.jwtspringsecurity.dto.AddTaskRequest;
import com.example.jwtspringsecurity.dto.ProjectCreationRequest;
import com.example.jwtspringsecurity.dto.ResponseUserProject;
import com.example.jwtspringsecurity.enities.Project;
import com.example.jwtspringsecurity.enities.Task;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjectsByUser(Long userId);

    List<Task> getTasksByProjectId(Long projectId);
    List<Task> refreshTasksByProjectId(Long projectId);
    List<ResponseUserProject> RESPONSE_USER_PROJECTS(Long projectId);

    Project createProject(ProjectCreationRequest request);

    //    Project updateProject(Long projectId,ProjectCreationRequest request);
    Project addTaskToProject(Long projectId, AddTaskRequest request);
//    Project addUserToProjectByEmail(Long projectId, AddUserByEmailRequest request);
}
