package com.auth.jwt.repository;

import com.auth.jwt.repository.user.ERole;
import com.auth.jwt.repository.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByRoleName(ERole roleName);
}
