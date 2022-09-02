package com.auth.jwt.service;

import com.auth.jwt.dto.request.EmailDetailsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public String sendEmail(EmailDetailsRequestDto emailDetailsRequestDto){
        var simpleMailMessage = new SimpleMailMessage();
        try {
            simpleMailMessage.setTo(emailDetailsRequestDto.getTo());
            simpleMailMessage.setFrom(emailDetailsRequestDto.getFrom());
            simpleMailMessage.setSubject("");
            simpleMailMessage.setText(emailDetailsRequestDto.getEmailBody());
            simpleMailMessage.setSentDate(new Date());
        }catch(Exception e){
            e.printStackTrace();
        }
        javaMailSender.send(simpleMailMessage);
        return "Email sent";
    }
}
