package com.auth.jwt.service;

import com.auth.jwt.dto.request.EmailDetailsRequestDto;
import com.auth.jwt.dto.request.EmailRequestOrderDto;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.List;

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


    public String sendOrderReceipt(String order_id, List<String> book_names, AppUser user, Double totalPrice, String paymentMethod){
        String userName = user.getFullName();
        String payment = paymentResponse(paymentMethod);
        var simpleMailMessage = new SimpleMailMessage();
        try {
            simpleMailMessage.setFrom("BookShop@gmail.com");
            simpleMailMessage.setTo(user.getEmail());
            simpleMailMessage.setText(
                    "Hi " + userName + ", your order with number " + order_id + " has been made."+
                            " Price you have to pay is " + totalPrice + ". Your order is: " + book_names + ". "
                            + payment);
            simpleMailMessage.setSentDate(new Date());
        }catch(Exception e){
            e.printStackTrace();
        }
        javaMailSender.send(simpleMailMessage);
        return "Email sent to: " + userName;
    }

    private String paymentResponse(String paymentMethod){
        if(paymentMethod.equalsIgnoreCase("ovo")){
            return "You need to transfer to 0812121212 (OVO)";
        } else if(paymentMethod.equalsIgnoreCase("BRI")){
            return "You need to transfer to 3222-1229-222-44-444 (BRI)";
        } else if(paymentMethod.equalsIgnoreCase("BCA")) {
            return "You need to transfer to 3333-333-123 (BCA)";
        }else{
            return "another payment method is not available";
        }
    }

    public void sendConfirmationSuccess(String order_id, AppUser user){
        String userName = user.getFullName();
        var simpleMailMessage = new SimpleMailMessage();
        try {
            simpleMailMessage.setFrom("Book@shop.com");
            simpleMailMessage.setTo(user.getEmail());
            simpleMailMessage.setText(
                    "Hi " + userName + ", your order with number " + order_id + " will be sent as soon as possible."+
                            " We will inform you after sending your books");
            simpleMailMessage.setSentDate(new Date());
        }catch(Exception e){
            e.printStackTrace();
        }
        javaMailSender.send(simpleMailMessage);
    }






}
