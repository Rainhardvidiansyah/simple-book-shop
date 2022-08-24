package com.auth.jwt.service;

import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.user.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @Transactional @Slf4j
public class AppUserService {

    @Autowired
    private UserRepo userRepo;


    public void editDataUser(AppUser user){
        AppUser appUser;

    }



}
