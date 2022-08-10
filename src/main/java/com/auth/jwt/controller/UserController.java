package com.auth.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
//
//    private final AppuserService userService;
//
//    public String testing() {
//        return "Halo";
//    }
//
//    private static List<AppUser> appUsers = List.of(
//            new AppUser(1l, "rainhard", "email", "password"),
//            new AppUser(2l, "Maulida", "maulidajelek", "si jelek"),
//            new AppUser(3l, "Hoki", "kucingpecek", "sihoki"),
//            new AppUser(4l, "Reno", "test", "test@gmail.com")
//
//    );
//
//    @GetMapping("/alluser")
//    public ResponseEntity<List<AppUser>> getAll() {
//        return ResponseEntity.ok().body(userService.getAllUser());
//    }
//
//    @GetMapping("/getappuser")
//    public List<AppUser> app(){
//        return appUsers;
//    }
//
//    @PostMapping("/save/user")
//    public ResponseEntity<AppUser>  saveUser(AppUser appuser){
//        return ResponseEntity.ok().body(userService.saveUser(appuser));
//    }
//
//    public ResponseEntity<Role> saveRole(Role role){
//        return ResponseEntity.ok().body(userService.saveRole(role));
//    }



}
