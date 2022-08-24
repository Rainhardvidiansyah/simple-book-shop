package com.auth.jwt.controller;

import com.auth.jwt.dto.request.UpdateProfileDto;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.user.AppUser;
import com.auth.jwt.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
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
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/data")
    @ResponseBody
    @PreAuthorize("authentication.principal.username == #username || hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public String getUserInfo(@RequestParam String name){
        var user = userRepo.findAppUserByFullName(name);
        return user.get().getEmail();
    }

    @GetMapping("{user_id}")
    public String getUser(@PathVariable("user_id") Long id){
        Optional<AppUser> user = userRepo.findById(id);
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(authentication.getPrincipal() != user){
            return "Wrong target";
        }else {
            return user.get().getFullName();
        }
    }

    @GetMapping("/user-auth")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String testUser(Authentication authentication){
        return String.format("Hallo %s", authentication.getName());
    }

    @GetMapping("/test-user-auth")
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

    @PutMapping("/update")
    @PreAuthorize("#userId == authentication.name or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public String updateData(@Param("userId") Long userId, @RequestBody UpdateProfileDto profileDto){
        Optional<AppUser> appUser = userRepo.findById(userId);
            var newUser = appUser.get();
            newUser.setEmail(profileDto.getEmail());
            newUser.setPassword(passwordEncoder.encode(profileDto.getPassword()));
            log.info(profileDto.getEmail());
            log.info(profileDto.getPassword());
            log.info(newUser.getEmail());
            log.info(newUser.getPassword());
            userRepo.save(newUser);
        return new String("Data successfully changed!");
    }

    @GetMapping()
    @PreAuthorize("#name == authentication.name or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public AppUser getData(@Param("name") String username){
        var user = userRepo.findAppUserByFullName(username);
        return user.get();
    }




}
