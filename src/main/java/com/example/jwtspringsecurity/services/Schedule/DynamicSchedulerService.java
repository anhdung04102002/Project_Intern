package com.example.jwtspringsecurity.services.Schedule;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import java.util.Set;
import java.util.concurrent.ScheduledFuture;

@Service
public class DynamicSchedulerService {
    private TaskScheduler taskScheduler; // đối tượng lập lịch tác vụ
    private Map<String, ScheduledFuture<?>> scheduledTasks = new HashMap<>();
    private Map<String, String> cronExpressions = new HashMap<>();

    public DynamicSchedulerService() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler(); //  sử dụng 1 pool thread để chạy các tác vụ
        threadPoolTaskScheduler.setPoolSize(5); // 5 thread có thể chạy đồng thời cùng một lúc
        threadPoolTaskScheduler.initialize(); // khởi tạo pool thread
        this.taskScheduler = threadPoolTaskScheduler; // gán pool thread cho taskScheduler
    }

    @PostConstruct
    public void init() {
        // Khởi tạo với bất kỳ tác vụ mặc định nào nếu cần
    }
    public void addCronJob(String jobId, Runnable task, String cronExpression) {
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, new CronTrigger(cronExpression));
        scheduledTasks.put(jobId, scheduledTask);
        cronExpressions.put(jobId, cronExpression);
    }
    public void removeCronJob(String jobId) {
        ScheduledFuture<?> scheduledTask = scheduledTasks.get(jobId);
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
            scheduledTasks.remove(jobId);
            cronExpressions.remove(jobId);

        }
    }
    public void updateCronJob(String jobId, Runnable task, String cronExpression) {
        removeCronJob(jobId);
        addCronJob(jobId, task, cronExpression);
    }
    public Map<String, String> getAllCronJobs() {
        return new HashMap<>(cronExpressions);
    }
}
