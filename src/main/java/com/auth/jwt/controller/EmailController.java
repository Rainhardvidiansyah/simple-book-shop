package com.auth.jwt.controller;

import com.auth.jwt.dto.request.EmailDetailsRequestDto;
import com.auth.jwt.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public String sendEmail(@RequestBody EmailDetailsRequestDto emailDetailsRequestDto){
        return emailService.sendEmail(emailDetailsRequestDto);
    }
}
