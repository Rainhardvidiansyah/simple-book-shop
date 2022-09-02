package com.auth.jwt.dto.exception;

import javax.naming.ldap.HasControls;
import java.util.HashMap;
import java.util.Map;

public class OrderNotFoundException extends RuntimeException{

    private String error_message;

    public OrderNotFoundException(String error_message) {
        super(error_message);
        this.error_message = error_message;
    }
}
