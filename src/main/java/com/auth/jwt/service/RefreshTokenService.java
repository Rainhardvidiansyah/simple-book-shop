package com.auth.jwt.service;

import com.auth.jwt.dto.exception.JwtTokenRefreshException;
import com.auth.jwt.dto.utils.repository.RefreshTokenRepo;
import com.auth.jwt.dto.utils.repository.UserRepo;
import com.auth.jwt.security.jwt.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j @RequiredArgsConstructor
public class RefreshTokenService {


    private static final long refreshTokenExp = 86400000;
    private final UserRepo userRepo;
    private final RefreshTokenRepo refreshTokenRepo;

    public RefreshToken findTokenById(Long id){
        var token = refreshTokenRepo.findById(id)
                .orElseThrow(RuntimeException::new);
        return token;
    }

    public Optional<RefreshToken> findToken(String token){
        return refreshTokenRepo.findRefreshTokenByToken(token);
    }

    public RefreshToken refreshToken(Long userId){
        var user = userRepo.findById(userId)
                .orElseThrow((() -> new RuntimeException()));
        var refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExp));
        refreshToken.setToken(UUID.randomUUID().toString());
        var savedToken = refreshTokenRepo.save(refreshToken);
        return savedToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new JwtTokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        var user = userRepo.findById(userId)
                .orElseThrow(RuntimeException::new);
        return refreshTokenRepo.deleteRefreshTokenByUser(user);
    }


}
