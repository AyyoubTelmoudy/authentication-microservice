package com.emsi.pfe.service.UserDetailsServiceImpl;

import com.emsi.pfe.security.SecurityConstants;
import com.emsi.pfe.service.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailingServiceImpl implements MailingService{

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendMail(String toMail,
                         String subject,
                         String body)
    {
        SimpleMailMessage message =new SimpleMailMessage();
        message.setFrom(SecurityConstants.APP_EMAIL);
        message.setTo(toMail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
    }

}
