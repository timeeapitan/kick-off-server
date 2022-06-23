package com.backend.service.impl;

import com.backend.model.Email;
import com.backend.repository.EmailRepository;
import com.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("timeeaioanapitan@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }

    @Override
    public String saveEmail(String toEmail, String subject, String body) {
        Email email = Email.builder().toEmail(toEmail).subject(subject).body(body).date(LocalDate.now()).build();
        emailRepository.save(email);
        return "Email sent successfully!";
    }

}
