package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.ProjectCreationRequest;
import com.example.jwtspringsecurity.enities.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ProjectMapper {
    Project projectCreations(ProjectCreationRequest projectCreationRequest);
    ProjectCreationRequest projectToProjectCreationRequest(Project project);
}
