package com.example.jwtspringsecurity.services.managerService;

import com.example.jwtspringsecurity.Mapper.ProjectMapper;
import com.example.jwtspringsecurity.Mapper.UserMapper;
import com.example.jwtspringsecurity.dto.ProjectCreationRequest;
import com.example.jwtspringsecurity.dto.ResponseUserProject;
import com.example.jwtspringsecurity.enities.Project;
import com.example.jwtspringsecurity.enities.Task;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.repositories.ProjectRepo;
import com.example.jwtspringsecurity.repositories.TaskRepo;
import com.example.jwtspringsecurity.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public List<Project> getAllProjectsByUser(Long userId) {
        return userRepo.findAllProjectsByUserId(userId);
    }

    @Override
    @Cacheable("projectsCache")
    public List<Task> getTasksByProjectId(Long projectId) {
        return projectRepo.findTasksByProjectId(projectId);
    }

    @Override
    public List<ResponseUserProject> RESPONSE_USER_PROJECTS(Long projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));

        List<User> users = project.getUsers(); // Assuming there's a getUsers method in Project entity
        List<ResponseUserProject> responseUserProjects = users.stream()
                .map(userMapper::userToResponseUserProject) // Assuming userMapper is already injected
                .collect(Collectors.toList());
        return responseUserProjects;

    }
    @Transactional
    @Override
    public Project createProject(ProjectCreationRequest request) {
        // Tạo đối tượng Project từ request
        Project project = projectMapper.projectCreations(request);

        // Lưu đối tượng Project trước để tránh lỗi TransientObjectException
        Project savedProject = projectRepo.save(project);

        // Liên kết với các User hiện có
        List<User> users = request.getUserIds().stream()
                .map(id -> userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id)))
                .peek(user -> user.getProjects().add(savedProject))
                .collect(Collectors.toList());
        project.setUsers(users);

        // Đảm bảo lưu các User hiện có để duy trì các thay đổi
        users.forEach(userRepo::save);

        // Tạo và liên kết các Task với Project
        List<Task> tasks = request.getTaskNames().stream()
                .map(taskName -> {
                    Task task = new Task();
                    task.setName(taskName);
                    task.setProject(savedProject);
                    return task;
                })
                .collect(Collectors.toList());
        project.setTasks(tasks);

        // Lưu lại Project với các User và Task đã liên kết
        return projectRepo.save(project);
    }
//    @Transactional
//    @Override
//    public Project updateProject(Long projectId, ProjectCreationRequest request) {
//        Project project = projectRepo.findById(projectId)
//                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
//        // Update project fields from request
//        projectMapper.projectToProjectCreationRequest(project, request);
//        // Handle users and tasks as needed
//        // Save and return the updated project
//        return projectRepo.save(project);
//    }

}
