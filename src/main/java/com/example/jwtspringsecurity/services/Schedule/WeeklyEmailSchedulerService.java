package com.example.jwtspringsecurity.services.Schedule;

import com.example.jwtspringsecurity.services.adminService.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeeklyEmailSchedulerService {
    @Autowired
    private EmailService emailService;

    @Autowired
    public WeeklyEmailSchedulerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 18 * * SUN")
    public void sendWeeklyEmail() {
        // Define recipient, subject, and message
        String to = "anhdung04102002@gmail.com";
        String subject = "Submit timesheet";
        String text = "Cuối tuần rồi, nhớ submit timesheet nhé!";

        // Use EmailService to send the email
        emailService.sendEmail(to, subject, text);
    }
}