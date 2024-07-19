package com.example.jwtspringsecurity.events;

import com.example.jwtspringsecurity.dto.TimeSheetWeekDTO;
import com.example.jwtspringsecurity.enities.TimesheetWeek;
import org.springframework.context.ApplicationEvent;

public class SubmitWeekEvent extends ApplicationEvent {
    private TimeSheetWeekDTO timeSheetWeekDTO;

    public SubmitWeekEvent(Object source, TimeSheetWeekDTO timeSheetWeekDTO) {
        super(source);
        this.timeSheetWeekDTO = timeSheetWeekDTO;
    }

    public TimeSheetWeekDTO getTimeSheetWeekDTO() {
        return timeSheetWeekDTO;
    }
}