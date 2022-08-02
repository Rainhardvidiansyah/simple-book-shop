package com.auth.jwt.service;

import com.auth.jwt.dto.request.RegistrationRequest;
import com.auth.jwt.repository.RoleRepo;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.user.AppUser;
import com.auth.jwt.user.ERole;
import com.auth.jwt.user.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RegistrationService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public AppUser registrationUser(AppUser user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        List<Role> roleUser = new ArrayList<>();
        if(user.getRoles().isEmpty()){
            Role roleAdmin = roleRepo.findRoleByRoleName(ERole.ADMIN).orElseThrow();
            //Role roleId = roleRepo.findById(1L).orElseThrow(); this works too, but tricky
            roleUser.add(roleAdmin);
            user.setRoles(roleUser);
        }

        List<Role> catManager = new ArrayList<>();
        if(user.getFullName().equalsIgnoreCase("hoki")){
            Role roleManager = roleRepo.findRoleByRoleName(ERole.MANAGER).orElseThrow();
            catManager.add(roleManager);
            user.setRoles(catManager);
        }
        return userRepo.save(user);
    }

    public boolean checkUserExisting(String email){
        boolean userPresentInDb = userRepo.findByEmail(email).isPresent();
        return userPresentInDb;
    }





}