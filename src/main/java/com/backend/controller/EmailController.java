package com.backend.controller;

import com.backend.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin
public class EmailController {

    private static final String confirmationSubject = "Confirmation mail from Kick-off app";
    private static final String confirmationBody = "Thank you for reaching out! \nYour message has been sent and we will try to get back to you as soon as possible! \nHave a great day!";
    private static final String businessMail = "timeeaioanapitan@gmail.com";

    @Autowired
    private EmailServiceImpl mailSender;

    @PostMapping("/contact-us-message")
    public String sendEmail(@RequestParam("toEmail") String toEmail,
                            @RequestParam("subject") String subject,
                            @RequestParam("body") String body) {

        mailSender.sendEmail(toEmail, confirmationSubject, confirmationBody);
        mailSender.sendEmail(businessMail, "The user " + toEmail + " sent a new message!", "The subject: \"" + subject + "\"" + "\nThe message: \"" + body + "\"");
        return mailSender.saveEmail(toEmail, subject, body);
    }
}
