package com.example.start.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.start.token.TokenManager;

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
    @Async
    public String sendemailoffre(String to,String nomentr,String descr) {
    
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Offre");
        message.setText("offre par "+nomentr+": /n "+descr);

       mailSender.send(message);

        return "ok";
    }
    @Async
    public String sendemailinscrit(String to,String descr) {
    
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("TIE JOB");
        message.setText(descr);

       mailSender.send(message);

        return "ok";
    }
    @Async
    public String sendemailinscritemployeur(String to) {
    
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("TIE JOB");
        message.setText("Nous vous souhaitons la bienvenue sur TIE JOB ! Votre compte employeur a été créé avec succès. \n"
        		+ "Nous sommes là pour vous aider à trouver les meilleurs talents pour votre établissement. N'hésitez pas à nous contacter si vous avez des questions ou besoin d'assistance pour utiliser notre site.\n"
        		+ "Merci de votre confiance en TIE JOB. Nous sommes impatients de vous aider à recruter les meilleurs talents du secteur de l'hôtellerie et de la restauration.\n"
        		+ "\n"
        		+ "Cordialement,\n"
        		+ "L'équipe de TIE JOB");

       mailSender.send(message);

        return "ok";
    }


    }
