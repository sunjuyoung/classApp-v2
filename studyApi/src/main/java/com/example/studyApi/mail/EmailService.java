package com.example.studyApi.mail;

import org.springframework.stereotype.Component;


public interface EmailService {

    void sendEmail(EmailMessage emailMessage);
}
