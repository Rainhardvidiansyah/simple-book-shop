package com.auth.jwt.controller;

import com.auth.jwt.dto.response.ResponseMessage;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final HttpServletRequest servletRequest;
    private final UserRepo userRepo;


    @GetMapping("/all-order")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllOrder(){
        String email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email);
        if(user.isEmpty()){
            return new ResponseEntity<>(generateFailedResponse(403, "GET", List.of("USER NOT VALID")),
                    HttpStatus.BAD_REQUEST);
        }
        var order = adminService.getAllOrder();
        return new ResponseEntity<>(generateSuccessResponse(200, "POST", order), HttpStatus.OK);
    }

    private ResponseMessage<Object> generateSuccessResponse(int code, String method, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(code);
        responseMessage.setMethod(method);
        responseMessage.setMessage(List.of("Success"));
        responseMessage.setData(object);
        return responseMessage;
    }

    private ResponseMessage<Object> generateFailedResponse(int code, String method, List<String> message){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(code);
        responseMessage.setMethod(method);
        responseMessage.setMessage(message);
        responseMessage.setData(null);
        return responseMessage;
    }

    private ResponseMessage<Object> generateFailedResponse(int code, String method, List<String> message, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(code);
        responseMessage.setMethod(null);
        responseMessage.setMessage(message);
        responseMessage.setData(object);
        return responseMessage;
    }


}
