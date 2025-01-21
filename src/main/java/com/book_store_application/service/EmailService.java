package com.book_store_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    public void sendPasswordResetEmail(String recipientEmail, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("sowndarya9632@gmail.com");
        message.setSubject("Password Reset Request");
        message.setText("Your temporary password is: " + tempPassword);
        javaMailSender.send(message);
    }
}
