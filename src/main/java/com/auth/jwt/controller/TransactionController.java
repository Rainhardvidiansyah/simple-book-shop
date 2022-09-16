package com.auth.jwt.controller;


import com.auth.jwt.dto.request.TransactionRequestDto;
import com.auth.jwt.dto.utils.ErrorUtils;
import com.auth.jwt.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/send-data")
    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> transaction(@RequestParam Long userid,
            @RequestBody TransactionRequestDto transactionDto, Errors errors){
        if(errors.hasErrors()){
            ErrorUtils.err(errors);
            return new ResponseEntity<>("You entered the wrong data", HttpStatus.BAD_REQUEST);
        }
        var transaction = transactionService.transaction(userid,
                transactionDto.getOrderNumber(),transactionDto.getTotalPrice(),
                transactionDto.getOrderNumber());
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
