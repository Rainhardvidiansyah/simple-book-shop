package com.auth.jwt.dto.utils.repository;

import com.auth.jwt.user.ERole;
import com.auth.jwt.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByRoleName(ERole roleName);
}
