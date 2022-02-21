package com.auth.jwt.service;

import com.auth.jwt.repository.RoleRepo;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.user.Appuser;
import com.auth.jwt.user.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @Transactional @Slf4j
public class AppuserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    public Appuser saveUser(Appuser appuser){
        return userRepo.save(appuser);
    }

    public Role saveRole(Role role){
        return roleRepo.save(role);
    }

    public void addRoleToUser(String email, String roleName){
        log.info("add user {} to role {}", email, roleName);
        Appuser appuser = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("test"));
        Role role = roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("testdua"));
        appuser.getRoles().add(role);
    }

    public Appuser getUserName(String email){
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Tidak ada"));
    }

    public Appuser fetchUserFullName(String fullName){
        return userRepo.findByFullName(fullName);
    }

    public List<Appuser> getAllUser(){
        return userRepo.findAll();
    }


}
