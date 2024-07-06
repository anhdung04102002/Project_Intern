//package com.example.jwtspringsecurity.Listener;
//
//import com.example.jwtspringsecurity.enities.TimeSheet;
//import com.example.jwtspringsecurity.events.SubmitWeekEvent;
//import com.example.jwtspringsecurity.events.TimesheetTemporarySaveEvent;
//import com.example.jwtspringsecurity.repositories.TimeSheetRepo;
//import com.example.jwtspringsecurity.repositories.TimesheetWeekRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Component
//public class TimesheetEventListener {
//
//    @Autowired
//    private TimeSheetRepo timesheetRepository;
//
//    @Autowired
//    private TimesheetWeekRepo timesheetWeekRepository;
//
//    @EventListener
//    public void handleTimesheetTemporarySave(TimesheetTemporarySaveEvent event) {
//        TimeSheet timesheet = event.getTimeSheet();
//        timesheetRepository.save(timesheet);
//    }
//    @EventListener
//    public void handleSubmitWeek(SubmitWeekEvent event) {
//        Long userId = event.getUserId();
//        LocalDate weekStartDate = event.getWeekStartDate();
//        LocalDate weekEndDate = event.getWeekEndDate();
//        List<TimeSheet> timesheets = event.getTimesheets();
//        timesheets.forEach(timesheet -> timesheet.setStatus("submitted"));
//        timesheetRepository.saveAll(timesheets);
//    }
//}
