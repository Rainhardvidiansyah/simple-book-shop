package com.auth.jwt.controller;


import com.auth.jwt.dto.exception.JwtTokenRefreshException;
import com.auth.jwt.dto.exception.LoginException;
import com.auth.jwt.dto.request.LoginRequestDto;
import com.auth.jwt.dto.request.TokenRequestDto;
import com.auth.jwt.dto.response.LoginResponseDto;
import com.auth.jwt.dto.response.ResponseMessage;
import com.auth.jwt.dto.response.TokenRefreshResponse;
import com.auth.jwt.security.jwt.JwtUtils;
import com.auth.jwt.security.jwt.RefreshToken;
import com.auth.jwt.service.AppUserService;
import com.auth.jwt.service.RefreshTokenService;
import com.auth.jwt.user.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final AppUserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils, RefreshTokenService refreshTokenService,
                          AppUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest){
        ResponseMessage<Object> response = new ResponseMessage<>();
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
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        var refreshToken = refreshTokenService.refreshToken(userDetails.getId());
        log.info("User logged in-> id {}, email: {}", userDetails.getId(), userDetails.getUsername());
        response.setCode(200);
        response.setMethod("POST");
        response.setMessage(List.of("Success"));
        response.setData(LoginResponseDto.userData(userDetails.getUsername(), jwt, refreshToken.getToken(), roles));
        return new ResponseEntity<>(response,
                HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        userService.deleteByUserId(userId);
        ResponseMessage<Object> responseMessage = new ResponseMessage<>();
        responseMessage.setCode(200);
        responseMessage.setMethod("GET");
        responseMessage.setMessage(List.of(String.format("User with id %d has logged out", userId)));
        responseMessage.setData(null);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
        var responseMessage = new ResponseMessage<Object>();
        return refreshTokenService.findToken(tokenRequestDto.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userData -> {
                    String token = jwtUtils.generateTokenFromUserName(userData.getFullName());
                    responseMessage.setCode(200);
                    responseMessage.setMethod("POST");
                    responseMessage.setMessage(List.of("Success"));
                    responseMessage.setData(new TokenRefreshResponse(token, tokenRequestDto.getRefreshToken()));
                    return ResponseEntity.ok().body(responseMessage);
                })
                .orElseThrow(() -> {
                    responseMessage.setCode(400);
                    responseMessage.setMessage(List.of("Failed to load refresh token"));
                    responseMessage.setData(new JwtTokenRefreshException(tokenRequestDto.getRefreshToken(),
                            "Refresh token not found!"));
                    return null;
                        });
    }





}
