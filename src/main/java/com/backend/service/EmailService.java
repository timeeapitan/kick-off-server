package com.backend.service;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String body);

    String saveEmail(String toEmail, String subject, String body);
}
