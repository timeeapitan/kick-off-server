package com.backend.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String toEmail;
    private String subject;
    private String body;
    private LocalDate date;

    @Override
    public String toString() {
        return "Email: " + toEmail + "\n" + "Subject: " + subject + "\n" + "Body: " + body + "\n" + "Date: " + date;
    }
}
