package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.TimeSheetDTO;
import com.example.jwtspringsecurity.enities.TimeSheet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface TimeSheetMapper {
    TimeSheet timeSheetDTOToTimeSheets(TimeSheetDTO timeSheetDTO);
    TimeSheetDTO timeSheetToTimeSheetDTO(TimeSheet timeSheet);
}
