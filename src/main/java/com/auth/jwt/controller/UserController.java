package com.auth.jwt.controller;

import com.auth.jwt.service.AppuserService;
import com.auth.jwt.user.Appuser;
import com.auth.jwt.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final AppuserService userService;

    public String testing() {
        return "Halo";
    }

    private static List<Appuser> appusers = List.of(
            new Appuser(1l, "rainhard", "email", "password"),
            new Appuser(2l, "Maulida", "maulidajelek", "si jelek"),
            new Appuser(3l, "Hoki", "kucingpecek", "sihoki"),
            new Appuser(4l, "Reno", "test", "test@gmail.com")

    );

    @GetMapping("/alluser")
    public ResponseEntity<List<Appuser>> getAll() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping("/getappuser")
    public List<Appuser> app(){
        return appusers;
    }

    @PostMapping("/save/user")
    public ResponseEntity<Appuser>  saveUser(Appuser appuser){
        return ResponseEntity.ok().body(userService.saveUser(appuser));
    }

    public ResponseEntity<Role> saveRole(Role role){
        return ResponseEntity.ok().body(userService.saveRole(role));
    }



}
