package com.auth.jwt.controller;


import com.auth.jwt.dto.request.TransactionRequestDto;
import com.auth.jwt.dto.response.ResponseMessage;
import com.auth.jwt.dto.response.TransactionResponseDto;
import com.auth.jwt.dto.utils.ErrorUtils;
import com.auth.jwt.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/send-proof")
    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> sendTransactionProof(@RequestParam Long userid,
            @RequestBody TransactionRequestDto transactionDto, Errors errors){
        if(errors.hasErrors()){
            return new ResponseEntity<>(generateFailedResponse(
                    List.of("You entered the wrong data"), ErrorUtils.err(errors)), HttpStatus.BAD_REQUEST);
        }
        var transaction = transactionService.transaction(userid,
                transactionDto.getOrderNumber(),transactionDto.getTotalPrice(),
                transactionDto.getPaymentMethod(), transactionDto.getSenderAccountNumber(), transactionDto.getSenderBank());
        log.info("Transaction Data: {}", TransactionResponseDto.from(transaction));
        return new ResponseEntity<>(generateSuccessResponse("POST", TransactionResponseDto.from(transaction)), HttpStatus.OK);
    }


    private ResponseMessage<Object> generateSuccessResponse(String method, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(200);
        responseMessage.setMethod(method);
        responseMessage.setMessage(List.of("Success"));
        responseMessage.setData(object);
        return responseMessage;
    }

    private ResponseMessage<Object> generateFailedResponse(List<String> message, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(400);
        responseMessage.setMethod(null);
        responseMessage.setMessage(message);
        responseMessage.setData(object);
        return responseMessage;
    }
}


