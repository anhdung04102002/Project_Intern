package com.example.jwtspringsecurity.controller.User;

import com.example.jwtspringsecurity.enities.TimeSheet;
import com.example.jwtspringsecurity.enities.TimesheetWeek;
import com.example.jwtspringsecurity.events.SubmitWeekEvent;
import com.example.jwtspringsecurity.events.TimesheetTemporarySaveEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/timesheet") // "api/user/timesheet
public class TimeSheetController {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/temporary-save")
    public ResponseEntity<TimeSheet> temporarySave(@RequestBody TimeSheet timeSheet) {
        TimesheetTemporarySaveEvent event = new TimesheetTemporarySaveEvent(this, timeSheet);
        eventPublisher.publishEvent(event);
        return ResponseEntity.ok(timeSheet);
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/submit-week")
    public ResponseEntity<TimesheetWeek> submitWeek(@RequestBody TimesheetWeek timesheetWeek) {
        SubmitWeekEvent event = new SubmitWeekEvent(this, timesheetWeek);
        eventPublisher.publishEvent(event);
        return ResponseEntity.ok(timesheetWeek);
    }
}

