package com.example.jwtspringsecurity.controller.Manager;

import com.example.jwtspringsecurity.dto.AddTaskRequest;
import com.example.jwtspringsecurity.dto.ProjectCreationRequest;
import com.example.jwtspringsecurity.dto.ResponseUserProject;
import com.example.jwtspringsecurity.enities.Project;
import com.example.jwtspringsecurity.enities.Task;
import com.example.jwtspringsecurity.services.managerService.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/projects_getTasks/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProjectId(@PathVariable Long projectId) {
        List<Task> tasks = projectService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/projects_refTasks/{projectId}")
    public ResponseEntity<List<Task>> refreshCache(@PathVariable Long projectId) {
        List<Task> tasks = projectService.refreshTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/projects_addTask/{projectId}")
    public ResponseEntity<?> addTaskToProject(@PathVariable Long projectId, @RequestBody AddTaskRequest request) {
        Project project = projectService.addTaskToProject(projectId, request);

        List<Task> tasks = projectService.getTasksByProjectId(projectId);

        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/users_of_project/{projectId}")
    public List<ResponseUserProject> getResponseUserProjects(@PathVariable Long projectId) {
        return projectService.RESPONSE_USER_PROJECTS(projectId);
    }
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/create_project")
    public ResponseEntity<Project> createProject(@RequestBody ProjectCreationRequest request) {
        Project createdProject = projectService.createProject(request);
        return ResponseEntity.ok(createdProject);
    }
}
