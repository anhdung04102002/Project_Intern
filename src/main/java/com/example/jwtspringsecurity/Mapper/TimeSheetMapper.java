package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.TimeSheetDTO;
import com.example.jwtspringsecurity.enities.TimeSheet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface TimeSheetMapper {
    @Mappings({
            @Mapping(source = "project", target = "project"),
            @Mapping(source = "task", target = "task"),
            @Mapping(source = "note", target = "note"),
            @Mapping(source = "work_time", target = "work_time"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "status", target = "status")
    })
    TimeSheet timeSheetDTOToTimeSheet(TimeSheetDTO timeSheetDTO);
    TimeSheetDTO timeSheetToTimeSheetDTO(TimeSheet timeSheet);
}
