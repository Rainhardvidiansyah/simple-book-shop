package com.auth.jwt.controller;

import com.auth.jwt.dto.request.UpdateProfileDto;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.service.AppUserService;
import com.auth.jwt.user.AppUser;
import com.auth.jwt.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserRepo userRepo;
    private final AppUserService userService;



    @ResponseBody
    @PreAuthorize("authentication.principal.username == #username || hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public String getUserInfo(@RequestParam String name){
        var user = userRepo.findAppUserByFullName(name);
        return user.get().getEmail();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    private String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return String.format("Hello Mr/Mrs %s", authentication.getName());
    }

    public String updateUserDetails(@PathVariable("userId") Long userId, Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        if(userDetails.getId() == userId){
        }
        return userDetails.getUsername();
    }


    @PreAuthorize("#name == authentication.name or " +
            "hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public String getData(@Param("name") String username){
        //var user = userRepo.findAppUserByFullName(username);
        return username.toUpperCase();
    }

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




}
