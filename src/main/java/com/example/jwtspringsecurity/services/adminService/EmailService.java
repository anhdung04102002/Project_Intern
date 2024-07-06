package com.example.jwtspringsecurity.services.adminService;
import java.lang.Thread;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender; // đây là interface của spring mail để gửi mail

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
    @Async
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);


    }

}