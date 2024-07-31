package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.ProjectCreationRequest;
import com.example.jwtspringsecurity.enities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface ProjectMapper {
    @Mappings({
           @Mapping(source = "name", target = "name"),
    })
    Project projectCreations(ProjectCreationRequest projectCreationRequest);
    ProjectCreationRequest projectToProjectCreationRequest(Project project);
}
