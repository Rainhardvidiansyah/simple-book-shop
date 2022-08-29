package com.auth.jwt.repository;

import com.auth.jwt.security.jwt.RefreshToken;
import com.auth.jwt.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findRefreshTokenByToken(String token);

    @Modifying
    int deleteRefreshTokenByUser(AppUser user);
}
