package com.auth.jwt.controller;

import com.auth.jwt.dto.request.UpdateProfileDto;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.service.AppUserService;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserRepo userRepo;
    private final AppUserService userService;



    @PreAuthorize("#_userid == principal.id or hasRole('ROLE_ADMIN')")
    public String doSomething(@RequestParam Long _userid){
        var user = userRepo.findById(_userid)
                .orElseThrow(RuntimeException::new);
        return String.format("Hello %s", user.getEmail());
    }

    @PutMapping("/update")
    @PreAuthorize("#_userid == principal.id or hasRole('ROLE_USER')")
    public ResponseEntity<?> updateUserProfile(@RequestParam Long _userid, @RequestBody UpdateProfileDto profileDto){
        if(profileDto.getEmail().isEmpty()){
            return new ResponseEntity<>("Email Cannot be blank!", HttpStatus.BAD_REQUEST);
        }
        var user = userService.editDataUser(_userid, AppUser.updateUserFrom(profileDto));
        log.info("New Data here: {}", UpdateProfileDto.From(user));
        return new ResponseEntity<>(UpdateProfileDto.From(user), HttpStatus.OK);
    }

    @GetMapping("/{id}/my-profile")
    @PreAuthorize("#id == principal.id or hasRole('ROLE_ADMIN')")
    public String profile(@PathVariable("id") Long id){
        var userID = userRepo.findById(id);
        if(userID.isEmpty()){
            return "Not found anything here";
        }
        return userID.get().getFullName();
    }

    @GetMapping()
    @PreAuthorize("#name == principal.username or hasRole('ROLE_ADMIN')")
    public String getProfileName(@RequestParam String name){
        return "Hello";
    }




}
