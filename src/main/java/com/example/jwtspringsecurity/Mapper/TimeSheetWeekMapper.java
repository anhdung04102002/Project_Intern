package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.TimeSheetWeekDTO;
import com.example.jwtspringsecurity.enities.TimesheetWeek;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface TimeSheetWeekMapper {
    TimesheetWeek timeSheetWeekDTOToTimeSheetWeek(TimeSheetWeekDTO timeSheetWeekDTO);
    TimeSheetWeekDTO timeSheetWeekToTimeSheetWeekDTO(TimesheetWeek timeSheetWeek);
}
