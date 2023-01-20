package com.brutus.sievn.services;

import com.brutus.sievn.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;

@Service
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    private EmailValidatorService emailValidatorService;

    @Value("${sievn.email.address}")
    private String toEmail;


    @Override
    @Async
    public void send(String email, String message, String name) {

        try {

            if(!emailValidatorService.test(email)){
                throw new BadRequestException("Invalid email");
            }

            String stringBuilder = "Name: " + name +
                    "\nEmail: " + email +
                    "\nMessage: " + message;

            javax.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(stringBuilder);
            helper.setTo(toEmail);
            helper.setSubject("SiEVN Real Estate Agency");
            helper.setSentDate(new Date());
            helper.setFrom(email);

            mailSender.send(mimeMessage);

        }catch(MessagingException e){

            LOGGER.error("Failed to send email", e);
            throw new BadRequestException("Failed to send email");
        }
    }

}
