package com.auth.jwt.dto.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtTokenRefreshException extends RuntimeException {
    public JwtTokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));

    }
}
