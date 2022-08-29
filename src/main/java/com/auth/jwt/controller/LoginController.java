package com.auth.jwt.controller;


import com.auth.jwt.dto.exception.JwtTokenRefreshException;
import com.auth.jwt.dto.exception.LoginException;
import com.auth.jwt.dto.request.LoginRequestDto;
import com.auth.jwt.dto.request.TokenRequestDto;
import com.auth.jwt.dto.response.LoginResponseDto;
import com.auth.jwt.dto.response.TokenRefreshResponse;
import com.auth.jwt.security.jwt.JwtUtils;
import com.auth.jwt.security.jwt.RefreshToken;
import com.auth.jwt.service.AppUserService;
import com.auth.jwt.service.RefreshTokenService;
import com.auth.jwt.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("api/v1/user")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;
    private final AppUserService userService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils, RefreshTokenService refreshTokenService, AppUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest){
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
        return new ResponseEntity<>(LoginResponseDto.userData(userDetails.getUsername(), jwt, refreshToken.getToken(), roles),
                HttpStatus.OK);
    }

    @GetMapping("/logout")
    @PreAuthorize("#userid == principal.id")
    public ResponseEntity<?> logoutUser(@RequestParam(required = true) long userid) {
        var user = userService.deleteByUserId(userid);
        return new ResponseEntity<>(String.format("User with %d has logged out", userid), HttpStatus.OK);
    }


    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
        return refreshTokenService.findToken(tokenRequestDto.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userData -> {
                    String token = jwtUtils.generateTokenFromUserName(userData.getFullName());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, tokenRequestDto.getRefreshToken()));
                })
                .orElseThrow(() -> new JwtTokenRefreshException(tokenRequestDto.getRefreshToken(),
                        "Refresh token not found!"));
    }



}
