package com.auth.jwt.service;

import com.auth.jwt.repository.RefreshTokenRepo;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @Transactional
@RequiredArgsConstructor
@Slf4j
public class AppUserService {


    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepo refreshTokenRepo;

    public AppUser editDataUser(Long id, AppUser user){
        var newUser = userRepo.findById(id)
                .orElseThrow(RuntimeException::new);
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        return userRepo.save(newUser);
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        var user = userRepo.findById(userId)
                .orElseThrow(RuntimeException::new);
        return refreshTokenRepo.deleteRefreshTokenByUser(user);
    }




}
