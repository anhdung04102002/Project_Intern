package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.AddTaskRequest;
import com.example.jwtspringsecurity.enities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface TaskMapper {
    @Mappings({
            @Mapping(source = "name", target = "name"),
//            @Mapping(source = "projectId", target = "project.id"),
    })
    Task taskRequestToTask(AddTaskRequest addTaskRequest);
}
