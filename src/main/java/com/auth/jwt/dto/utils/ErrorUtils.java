package com.auth.jwt.dto.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ErrorUtils {

    public static List<String> err(Errors errors){
        List<String> messages = new ArrayList<>();
        for(ObjectError objectError : errors.getAllErrors()){
            messages.add(objectError.getDefaultMessage());
        }
        return messages;
    }
}
