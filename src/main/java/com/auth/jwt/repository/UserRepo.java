package com.auth.jwt.repository;

import com.auth.jwt.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    AppUser findByFullName(String fullName);
}
