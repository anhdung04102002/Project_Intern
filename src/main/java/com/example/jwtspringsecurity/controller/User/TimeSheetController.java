package com.example.jwtspringsecurity.controller.User;

import com.example.jwtspringsecurity.Mapper.TimeSheetMapper;
import com.example.jwtspringsecurity.dto.TimeSheetDTO;
import com.example.jwtspringsecurity.dto.TimeSheetWeekDTO;
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
    @Autowired
    private TimeSheetMapper timeSheetMapper;
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/temporary-save")
    public ResponseEntity<TimeSheetDTO> temporarySave(@RequestBody TimeSheetDTO timeSheetDTO) {
        TimesheetTemporarySaveEvent event = new TimesheetTemporarySaveEvent(this, timeSheetDTO);
        eventPublisher.publishEvent(event);
        return ResponseEntity.ok(event.getTimeSheetDTO());
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/submit-week")
    public ResponseEntity<TimeSheetWeekDTO> submitWeek(@RequestBody TimeSheetWeekDTO timeSheetWeekDTO) {
        SubmitWeekEvent event = new SubmitWeekEvent(this, timeSheetWeekDTO);
        eventPublisher.publishEvent(event);
        return ResponseEntity.ok(event.getTimeSheetWeekDTO());
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTimeSheet(@PathVariable Long id) {
        TimeSheet timeSheet = timesheetRepository.findById(id).orElse(null);
        if (timeSheet == null) {
            return ResponseEntity.notFound().build();
        }
        timesheetRepository.delete(timeSheet);
        return ResponseEntity.ok().body("Đã xóa thành công ");
    }
}

