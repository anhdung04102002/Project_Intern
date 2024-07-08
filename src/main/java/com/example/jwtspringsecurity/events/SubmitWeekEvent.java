package com.example.jwtspringsecurity.events;

import com.example.jwtspringsecurity.enities.TimesheetWeek;
import org.springframework.context.ApplicationEvent;

public class SubmitWeekEvent extends ApplicationEvent {
    private TimesheetWeek timesheetWeek;

    public SubmitWeekEvent(Object source, TimesheetWeek timesheetWeek) {
        super(source);
        this.timesheetWeek = timesheetWeek;
    }

    public TimesheetWeek getTimesheetWeek() {
        return timesheetWeek;
    }
}