package com.example.jwtspringsecurity.controller.User;

import com.example.jwtspringsecurity.enities.TimeSheet;
import com.example.jwtspringsecurity.enities.TimesheetWeek;
import com.example.jwtspringsecurity.events.SubmitWeekEvent;
import com.example.jwtspringsecurity.events.TimesheetTemporarySaveEvent;
import com.example.jwtspringsecurity.repositories.TimeSheetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/timesheet") // "api/user/timesheet
public class TimeSheetController {
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private TimeSheetRepo timesheetRepository;

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
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTimeSheet(@PathVariable Long id) {
        TimeSheet timeSheet = timesheetRepository.findById(id).orElse(null);
        if (timeSheet == null) {
            return ResponseEntity.notFound().build();
        }
        TimesheetTemporarySaveEvent event = new TimesheetTemporarySaveEvent(this, timeSheet);
        eventPublisher.publishEvent(event);
        return ResponseEntity.ok().body("Đã xóa thành công ");
    }
}

