package com.example.jwtspringsecurity.events;


import com.example.jwtspringsecurity.enities.TimeSheet;
import org.springframework.context.ApplicationEvent;

public class TimesheetTemporarySaveEvent extends ApplicationEvent {
    private TimeSheet timeSheet;

    public TimesheetTemporarySaveEvent(Object source,TimeSheet timeSheet) {
        super(source);
        this.timeSheet = timeSheet;

    }
    public TimeSheet getTimeSheet() {
        return timeSheet;
    }
}