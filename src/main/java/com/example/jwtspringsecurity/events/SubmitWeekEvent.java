package com.example.jwtspringsecurity.events;

import com.example.jwtspringsecurity.enities.TimeSheet;
import org.springframework.context.ApplicationEvent;
import java.time.LocalDate;
import java.util.List;

public class SubmitWeekEvent extends ApplicationEvent {
    private  Long userId;
    private  LocalDate weekStartDate;
    private  LocalDate weekEndDate;
    private List<TimeSheet> timesheets;

    public SubmitWeekEvent(Object source, Long userId, LocalDate weekStartDate, LocalDate weekEndDate, List<TimeSheet> timesheets) {
        super(source);
        this.userId = userId;
        this.weekStartDate = weekStartDate;
        this.weekEndDate = weekEndDate;
        this.timesheets = timesheets;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDate getWeekStartDate() {
        return weekStartDate;
    }

    public LocalDate getWeekEndDate() {
        return weekEndDate;
    }
    public List<TimeSheet> getTimesheets() {
        return timesheets;
    }
}
