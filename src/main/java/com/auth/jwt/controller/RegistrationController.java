package com.auth.jwt.controller;

import com.auth.jwt.dto.request.RegistrationRequest;
import com.auth.jwt.dto.response.RegistrationResponseDto;
import com.auth.jwt.dto.response.ResponseMessage;
import com.auth.jwt.service.RegistrationService;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationRequest request, Errors errors){
        boolean userExisting = registrationService.checkUserExisting(request.getEmail());
        if(userExisting){
            return new ResponseEntity<>(generateFailedResponse("POST", List.of(String.format("%s has been registered", request.getEmail()))),
                    HttpStatus.BAD_REQUEST);
        }
        if(errors.hasErrors()){
            return new ResponseEntity<>(generateFailedResponse("POST", List.of(""), errorMessages(errors)), HttpStatus.BAD_REQUEST);
        }
        AppUser user = new AppUser();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        AppUser savedUser = registrationService.registrationUser(user);
        log.info("User just registered {}", RegistrationResponseDto.From(savedUser));
        return new ResponseEntity<>(generateSuccessResponse(201, "POST", RegistrationResponseDto.From(savedUser)), HttpStatus.CREATED);
    }

    private static List<String> errorMessages(Errors errors){
        List<String> errorMessages = new ArrayList<>();
        if(errors.hasErrors()){
            for(ObjectError objectError : errors.getAllErrors()){
                errorMessages.add(objectError.getDefaultMessage());
            }
        }
        return errorMessages;
    }

    private ResponseMessage<Object> generateSuccessResponse(int code, String method, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(code);
        responseMessage.setMethod(method);
        responseMessage.setMessage(List.of("Success"));
        responseMessage.setData(object);
        return responseMessage;
    }

    private ResponseMessage<Object> generateFailedResponse(String method, List<String> message, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(400);
        responseMessage.setMethod(method);
        responseMessage.setMessage(message);
        responseMessage.setData(object);
        return responseMessage;
    }
    private ResponseMessage<Object> generateFailedResponse(String method, List<String> message){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(400);
        responseMessage.setMethod(method);
        responseMessage.setMessage(message);
        responseMessage.setData(null);
        return responseMessage;
    }


}