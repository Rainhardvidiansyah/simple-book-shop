package com.auth.jwt.controller;


import com.auth.jwt.dto.exception.LoginException;
import com.auth.jwt.dto.request.LoginRequestDto;
import com.auth.jwt.dto.response.LoginResponseDto;
import com.auth.jwt.security.jwt.JwtUtils;
import com.auth.jwt.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/user")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginFunc(@RequestBody LoginRequestDto loginRequest){
        try {
            if(loginRequest.getEmail().isEmpty() && loginRequest.getEmail().isBlank()){
                throw new LoginException("Please input your email");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.createToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        return new ResponseEntity<>(LoginResponseDto.userData(userDetails.getUsername(), jwt, roles),
                HttpStatus.OK);
    }


}
