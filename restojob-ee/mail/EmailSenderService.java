package com.rhplateforme.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rhplateforme.token.TokenManager;

import java.io.File;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Async
    public String sendVerificationEmail(String to) {
        String verificationToken = TokenManager.generateToken(to);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText("Your verification code is: " + verificationToken);

       mailSender.send(message);

        return verificationToken;
    }


    }
