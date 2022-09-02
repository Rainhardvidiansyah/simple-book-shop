package com.auth.jwt.service;

import com.auth.jwt.dto.request.EmailDetailsRequestDto;
import com.auth.jwt.dto.request.EmailRequestOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public String sendEmail(EmailDetailsRequestDto emailDetails){
        var simpleMailMessage = new SimpleMailMessage();
        try {
            simpleMailMessage.setTo(emailDetails.getTo());
            simpleMailMessage.setFrom(emailDetails.getFrom());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getEmailBody());
            simpleMailMessage.setSentDate(new Date());
        }catch(Exception e){
            e.printStackTrace();
        }
        javaMailSender.send(simpleMailMessage);
        return "Email sent";
    }


    public String sendEmailWithAttachment(EmailDetailsRequestDto emailDetails){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try{
            mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setTo(emailDetails.getTo());
            mimeMessageHelper.setFrom(emailDetails.getFrom());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            mimeMessageHelper.setSentDate(new Date());

            FileSystemResource file
                    = new FileSystemResource(
                    new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);
            javaMailSender.send(message);
            return "Email sent successfully";
        }catch(MessagingException e) {
            return "Email cannot be sent. Error's happening!";
        }
    }

    public String sendOrderDataToUser(EmailRequestOrderDto emailOrderDto){
        var simpleMailMessage = new SimpleMailMessage();
        try {
            simpleMailMessage.setText(emailOrderDto.getOrder_id());
            simpleMailMessage.setFrom(emailOrderDto.getFrom());
            simpleMailMessage.setTo(emailOrderDto.getTo());
            simpleMailMessage.setSentDate(new Date());
        }catch(Exception e){
            e.printStackTrace();
        }
        javaMailSender.send(simpleMailMessage);
        return "Email sent";
    }





}
