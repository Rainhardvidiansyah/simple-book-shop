package com.auth.jwt.controller;

import com.auth.jwt.dto.request.RegistrationRequest;
import com.auth.jwt.service.RegistrationService;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest request){
        AppUser user = new AppUser();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        AppUser savedUser = registrationService.registrationUser(user);
        log.info("User just registered {}", user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
}

//AppUser appUser = AppUser.createUserFrom(request); make this password encoded!
//        log.info("User from static {}", appUser);