package com.example.jwtspringsecurity.controller.Admin;

import com.example.jwtspringsecurity.services.Schedule.DynamicSchedulerService;
import com.example.jwtspringsecurity.services.Schedule.WeeklyEmailSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/schedule")
public class ScheduleController {
    @Autowired
    private DynamicSchedulerService dynamicSchedulerService;
    @Autowired
    private WeeklyEmailSchedulerService weeklyEmailSchedulerService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addCronJob(@RequestParam String jobId, @RequestParam String cronExpression) {
        if ("weeklyEmailJob".equals(jobId)) {
            dynamicSchedulerService.addCronJob(jobId, weeklyEmailSchedulerService::sendWeeklyEmail, cronExpression);
        } else {
            dynamicSchedulerService.addCronJob(jobId, () -> {
                // Định nghĩa tác vụ cần thực hiện
                System.out.println("Executing task for job: " + jobId);
            }, cronExpression);
        }
        return "Cron job added";
    }

    @DeleteMapping("/remove")
    public String removeCronJob(@RequestParam String jobId) {
        dynamicSchedulerService.removeCronJob(jobId);
        return "Cron job removed";
    }

    @PutMapping("/update")
    public String updateCronJob(@RequestParam String jobId, @RequestParam String cronExpression) {
        if ("weeklyEmailJob".equals(jobId)) {
            dynamicSchedulerService.updateCronJob(jobId, weeklyEmailSchedulerService::sendWeeklyEmail, cronExpression);
        } else {
            dynamicSchedulerService.updateCronJob(jobId, () -> {
                // Định nghĩa tác vụ cần thực hiện
                System.out.println("Executing updated task for job: " + jobId);
            }, cronExpression);
        }
        return "Cron job updated";
    }

    @GetMapping("/list")
    public Map<String,String> listCronJobs() {
        return dynamicSchedulerService.getAllCronJobs();
    }
}