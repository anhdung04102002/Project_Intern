package com.example.jwtspringsecurity.controller.User;

import com.example.jwtspringsecurity.services.Schedule.WeeklyEmailSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/user")
public class EmailSchedulingController {

    @Autowired
    private  WeeklyEmailSchedulerService weeklyEmailSchedulerService;




    @GetMapping("/sendWeekly")
    public ResponseEntity<String> triggerWeeklyEmail() {
        weeklyEmailSchedulerService.sendWeeklyEmail();
        System.out.println("Quá trình gửi email hàng tuần đã được kích hoạt.");
        return ResponseEntity.ok("Quá trình gửi email hàng tuần đã được kích hoạt.");
    }
}