package com.example.jwtspringsecurity.Listener;

import com.example.jwtspringsecurity.Mapper.AttendanceMapper;
import com.example.jwtspringsecurity.Mapper.TimeSheetMapper;
import com.example.jwtspringsecurity.Mapper.TimeSheetWeekMapper;
import com.example.jwtspringsecurity.dto.TimeSheetDTO;
import com.example.jwtspringsecurity.dto.TimeSheetWeekDTO;
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
    private TimeSheetMapper timeSheetMapper;
    @Autowired
    private TimesheetWeekRepo timesheetWeekRepository;
    @Autowired
    private TimeSheetWeekMapper timeSheetWeekMapper;
    @Transactional
    @EventListener
    public void handleTimesheetTemporarySave(TimesheetTemporarySaveEvent event) {
        // việc xóa và cập nhật phải xảy ra trong một transaction
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail)); ;

        TimeSheetDTO timeSheetDTO = event.getTimeSheetDTO();
        timeSheetDTO.setStatus("new");
        TimeSheet newTimesheet = timeSheetMapper.timeSheetDTOToTimeSheets(timeSheetDTO);

        newTimesheet.setUser(currentUser);


        TimeSheet existingTimesheet = timesheetRepository.findByDateAndUser(newTimesheet.getDate(), currentUser);
        if (existingTimesheet != null) {
            // If an existing TimeSheet is found, delete it or update it
            // To delete:
//            timesheetRepository.delete(existingTimesheet);
//            timesheetRepository.flush(); // đánh dấu lệnh delete thưc hiện ngay lập tức( tránh việc delete chưa được thực hiện update thực hiện gây xung đột)
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
        TimeSheetWeekDTO timeSheetWeekDTO = event.getTimeSheetWeekDTO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail)); ;
        TimesheetWeek timesheetWeek = timeSheetWeekMapper.timeSheetWeekDTOToTimeSheetWeek(timeSheetWeekDTO);
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
        timeSheetWeekDTO.setTotalHours(totalHours); // để hiển thị kết quả trả về cho client

        // Save the TimesheetWeek object to the repository
        timesheetWeekRepository.save(timesheetWeek);
    }
//    @EventListener
//    public void delete(TimesheetTemporarySaveEvent timesheetTemporarySaveEvent) {
//        TimeSheetDTO timeSheetDTO = timesheetTemporarySaveEvent.getTimeSheetDTO();
//        TimeSheet timeSheet = timeSheetMapper.timeSheetDTOToTimeSheet(timeSheetDTO);
//        timesheetRepository.delete(timeSheet);
//    }
}
