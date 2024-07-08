package com.example.jwtspringsecurity.Listener;

import com.example.jwtspringsecurity.enities.TimeSheet;
import com.example.jwtspringsecurity.enities.TimesheetWeek;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.events.SubmitWeekEvent;
import com.example.jwtspringsecurity.events.TimesheetTemporarySaveEvent;
import com.example.jwtspringsecurity.repositories.TimeSheetRepo;
import com.example.jwtspringsecurity.repositories.TimesheetWeekRepo;
import com.example.jwtspringsecurity.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class TimesheetEventListener {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private TimeSheetRepo timesheetRepository;

    @Autowired
    private TimesheetWeekRepo timesheetWeekRepository;
    @Transactional
    @EventListener
    public void handleTimesheetTemporarySave(TimesheetTemporarySaveEvent event) {
        // việc xóa và cập nhật phải xảy ra trong một transaction
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail)); ;

        TimeSheet newTimesheet = event.getTimeSheet();
        newTimesheet.setStatus("new");
        newTimesheet.setUser(currentUser);

        // Find an existing TimeSheet with the same date
//        TimeSheet existingTimesheet = timesheetRepository.findByDate(newTimesheet.getDate());
        TimeSheet existingTimesheet = timesheetRepository.findByDateAndUser(newTimesheet.getDate(), currentUser);
        if (existingTimesheet != null) {
            // If an existing TimeSheet is found, delete it or update it
            // To delete:
            timesheetRepository.delete(existingTimesheet);
            timesheetRepository.flush(); // đánh dấu lệnh delete thưc hiện ngay lập tức( tránh việc delete chưa được thực hiện update thực hiện gây xung đột)
            // Or to update:
            existingTimesheet.setProject(newTimesheet.getProject());
            existingTimesheet.setTask(newTimesheet.getTask());
            existingTimesheet.setNote(newTimesheet.getNote());
            existingTimesheet.setWork_time(newTimesheet.getWork_time());
            existingTimesheet.setType(newTimesheet.getType());
            existingTimesheet.setUser(currentUser);
            existingTimesheet.setStatus(newTimesheet.getStatus());

            timesheetRepository.save(existingTimesheet);
        } else {
            // If no existing TimeSheet is found, save the new one
            timesheetRepository.save(newTimesheet);
        }
    }
    @Transactional
    @EventListener
    public void handleSubmitWeek(SubmitWeekEvent event) {
        TimesheetWeek timesheetWeek = event.getTimesheetWeek();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail)); ;
        timesheetWeek.setUser(currentUser);
        Long userId = currentUser.getId();
        LocalDate weekStartDate = timesheetWeek.getWeekStartDate();
        LocalDate weekEndDate = timesheetWeek.getWeekEndDate();

        // Find all TimeSheets of the user within the week
        List<TimeSheet> timesheets = timesheetRepository.findAllByUserAndDateBetween(userId, weekStartDate, weekEndDate);

        timesheets.forEach(timesheet -> timesheet.setStatus("submitted"));
        timesheetRepository.saveAll(timesheets);

        double totalHours = timesheets.stream()
                .mapToDouble(TimeSheet::getWork_time)
                .sum();

        // Update the TimesheetWeek object
        timesheetWeek.setTotalHours(totalHours);

        // Save the TimesheetWeek object to the repository
        timesheetWeekRepository.save(timesheetWeek);
    }
}
