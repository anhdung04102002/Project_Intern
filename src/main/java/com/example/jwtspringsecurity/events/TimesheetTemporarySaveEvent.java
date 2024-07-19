package com.example.jwtspringsecurity.events;


import com.example.jwtspringsecurity.dto.TimeSheetDTO;
import com.example.jwtspringsecurity.enities.TimeSheet;
import org.springframework.context.ApplicationEvent;

public class TimesheetTemporarySaveEvent extends ApplicationEvent {
    private TimeSheetDTO timeSheetDTO;

    public TimesheetTemporarySaveEvent(Object source, TimeSheetDTO timeSheetDTO) {
        super(source);
        this.timeSheetDTO = timeSheetDTO;
    }

    public TimeSheetDTO getTimeSheetDTO() {
        return timeSheetDTO;
    }
}