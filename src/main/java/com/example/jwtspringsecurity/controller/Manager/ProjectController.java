package com.example.jwtspringsecurity.controller.Manager;

import com.example.jwtspringsecurity.enities.Project;
import com.example.jwtspringsecurity.enities.Task;
import com.example.jwtspringsecurity.services.managerService.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ProjectController  {
    @Autowired
    private ProjectService projectService;
    @PreAuthorize("hasRole('MANAGER') or hasRole('USER')")
    @GetMapping("/project_getAll/{userId}")
    public ResponseEntity<List<Project>> getAllProjectsByUser(@PathVariable Long userId) {
        List<Project> projects = projectService.getAllProjectsByUser(userId);
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/projects_getTasks/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProjectId(@PathVariable Long projectId) {
        List<Task> tasks = projectService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }
}
