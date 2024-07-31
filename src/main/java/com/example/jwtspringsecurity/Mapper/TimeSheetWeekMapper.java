package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.TimeSheetWeekDTO;
import com.example.jwtspringsecurity.enities.TimesheetWeek;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface TimeSheetWeekMapper {
    @Mappings({
            @Mapping(source = "weekStartDate", target = "weekStartDate"),
            @Mapping(source = "weekEndDate", target = "weekEndDate"),
            @Mapping(source = "totalHours", target = "totalHours")
    })
    TimesheetWeek timeSheetWeekDTOToTimeSheetWeeks(TimeSheetWeekDTO timeSheetWeekDTO);
    TimeSheetWeekDTO timeSheetWeekToTimeSheetWeekDTO(TimesheetWeek timeSheetWeek);
}
