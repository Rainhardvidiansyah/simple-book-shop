package com.auth.jwt.controller;

import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.user.AppUser;
import com.auth.jwt.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserRepo userRepo;

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

    @GetMapping("/test-string")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public String test(){
        return "Halo";
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String testUser(Authentication authentication){
        return String.format("Hallo %s", authentication.getName());
    }

    @GetMapping("/test-user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    private String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return String.format("Hello Mr/Mrs %s", authentication.getName());
    }

    @GetMapping("/test-user-2")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    private String getLoggedInUserII() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return String.format("Hello Mr/Mrs %s", authentication.getName());
    }

    @GetMapping("/check-user-data/{name}")
    public String principe(Principal principal, @PathVariable("name") String name){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
                AppUser appUser = userRepo.findAppUserByEmail(email).orElseThrow(RuntimeException::new);
        if(principal.getName() != name){
            return "Wrong data";
        }
        return "Data accessible";
    }



}
