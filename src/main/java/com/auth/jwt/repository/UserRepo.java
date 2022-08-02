package com.auth.jwt.repository;

import com.auth.jwt.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
    AppUser findByFullName(String fullName);

    @Query("select a from AppUser a where a.fullName = ?1")
    public String userNameCannotBeSame(String fullName);
}
