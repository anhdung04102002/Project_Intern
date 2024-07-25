package com.example.jwtspringsecurity.services.Schedule;

import com.example.jwtspringsecurity.services.adminService.EmailService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeeklyEmailSchedulerService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private DynamicSchedulerService dynamicSchedulerService;
    @Autowired
    public WeeklyEmailSchedulerService(EmailService emailService) {
        this.emailService = emailService;
    }
    @PostConstruct
    public void init() {
        scheduleWeeklyEmail();
    }
    public void scheduleWeeklyEmail() {
        dynamicSchedulerService.addCronJob("weeklyEmailJob", this::sendWeeklyEmail, "0 0 18 * * SUN");
    }
//    @Scheduled(cron = "0 0 18 * * SUN") // giây/phút/giờ/ngày/tháng/ngày trong tuần
    public void sendWeeklyEmail() {
        // Define recipient, subject, and message
        String to = "anhdung04102002@gmail.com";
        String subject = "Submit timesheet";
        String text = "Cuối tuần rồi, nhớ submit timesheet nhé!";

        // Use EmailService to send the email
        emailService.sendEmail(to, subject, text);
    }
}