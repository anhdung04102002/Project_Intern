package com.example.jwtspringsecurity.services.managerService;

import com.example.jwtspringsecurity.enities.Project;
import com.example.jwtspringsecurity.enities.Task;
import com.example.jwtspringsecurity.repositories.ProjectRepo;
import com.example.jwtspringsecurity.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserRepo userRepo;
    @Override
    public List<Project> getAllProjectsByUser(Long userId) {
        return userRepo.findAllProjectsByUserId(userId);
    }

    @Override
    @Cacheable("projectsCache")
    public List<Task> getTasksByProjectId(Long projectId) {
        return projectRepo.findTasksByProjectId(projectId);
    }
}
