package com.auth.jwt.controller;

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
        var user = userRepo.findAppUserByEmail(email);
        var allHistory = historyService.getMyHistory(user.get());
        if(allHistory.isEmpty()){
            return new ResponseEntity<>("History not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allHistory, HttpStatus.OK);
    }

}
