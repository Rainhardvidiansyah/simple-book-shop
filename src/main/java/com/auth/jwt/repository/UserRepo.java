package com.auth.jwt.repository;

import com.auth.jwt.user.Appuser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Appuser, Long> {

    Optional<Appuser> findByEmail(String email);

    Appuser findByFullName(String fullName);
}
