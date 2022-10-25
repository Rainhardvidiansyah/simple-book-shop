package com.auth.jwt.controller;

import com.auth.jwt.dto.response.HistoryResponseDto;
import com.auth.jwt.dto.response.ResponseMessage;
import com.auth.jwt.model.History;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final HttpServletRequest servletRequest;
    private final UserRepo userRepo;

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole ('ROLE_USER')")
    public ResponseEntity<?> getMyHistory(){
        String email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email).orElseThrow(RuntimeException::new);
        var histories = historyService.getMyHistory(user);
        if(histories.isEmpty()){
            return new ResponseEntity<>(generateFailedResponse(404, "GET", List.of("You don't have any histories")), HttpStatus.NOT_FOUND);
        }
        List<HistoryResponseDto> historyResponses = new ArrayList<>();
        for(History history: histories){
            historyResponses.add(HistoryResponseDto.from(history));
        }
        return new ResponseEntity<>(generateSuccessResponse("GET", historyResponses), HttpStatus.OK);
    }

    private ResponseMessage<Object> generateSuccessResponse(String method, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(200);
        responseMessage.setMethod(method);
        responseMessage.setMessage(List.of("Success"));
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

    private ResponseMessage<Object> generateFailedResponse(int code, String method, List<String> message){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(code);
        responseMessage.setMethod(method);
        responseMessage.setMessage(message);
        responseMessage.setData(null);
        return responseMessage;
    }


}
