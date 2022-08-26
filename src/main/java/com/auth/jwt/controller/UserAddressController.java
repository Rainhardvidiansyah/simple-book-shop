package com.auth.jwt.controller;


import com.auth.jwt.dto.request.AddressRequestDto;
import com.auth.jwt.dto.response.AddressResponseDto;
import com.auth.jwt.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserAddressController {

    private final UserAddressService addressService;

    @PostMapping("/{user_id}/save-address")
    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> insertAddress(@PathVariable("user_id") Long userid,
                                           @RequestBody @Valid AddressRequestDto addressDto, Errors errors){
        Map<String, Object> errResponse = new HashMap<>();
        if(errors.hasErrors()){
            errResponse.put("Error ... Address not complete", err(errors));
            return new ResponseEntity<>(errResponse, HttpStatus.BAD_REQUEST);
        }
        var address = addressService.saveAddress(userid, addressDto.getCompleteAddress(),
                addressDto.getPhoneNumber(), addressDto.getPostalCode());
        Map<String, Object> response = new HashMap<>();
        response.put("User address successfully added", AddressResponseDto.From(address));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private static List<String> err(Errors errors){
        List<String> errMessage = new ArrayList<>();
        if(errors.hasErrors()){
            for(ObjectError objectError : errors.getAllErrors()){
                errMessage.add(objectError.getDefaultMessage());
            }
        }
        return errMessage;
    }

}
