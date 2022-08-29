package com.auth.jwt.service;

import com.auth.jwt.repository.RoleRepo;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.user.AppUser;
import com.auth.jwt.user.ERole;
import com.auth.jwt.user.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
            //Role roleAdmin = roleRepo.findRoleByRoleName(ERole.ROLE_ADMIN).orElseThrow();
            //Role roleId = roleRepo.findById(1L).orElseThrow(); this works too, but tricky
            Role roleForUser = roleRepo.findRoleByRoleName(ERole.ROLE_USER).orElseThrow();
            roleUser.add(roleForUser);
            user.setRoles(roleUser);
        }

        List<Role> cat = new ArrayList<>();
        if(user.getFullName().equalsIgnoreCase("hoki")){
            Role roleManager = roleRepo.findRoleByRoleName(ERole.ROLE_MANAGER).orElseThrow();
            Role roleUserCat = roleRepo.findRoleByRoleName(ERole.ROLE_USER).orElseThrow();
            cat.add(roleManager);
            cat.add(roleUserCat);
            user.setRoles(cat);
        }
        return userRepo.save(user);
    }

    public boolean checkUserExisting(String email){
        boolean userPresentInDb = userRepo.findAppUserByEmail(email).isPresent();
        return userPresentInDb;
    }





}
