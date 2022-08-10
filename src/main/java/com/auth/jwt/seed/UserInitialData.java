package com.auth.jwt.seed;

import com.auth.jwt.repository.RoleRepo;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.repository.user.AppUser;
import com.auth.jwt.repository.user.ERole;
import com.auth.jwt.repository.user.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserInitialData implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserInitialData(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        Role userRole = new Role(ERole.ROLE_USER);
        Role managerRole = new Role(ERole.ROLE_MANAGER);
        roleRepo.saveAll(List.of(adminRole, userRole, managerRole));


        AppUser user = new AppUser();
        user.setFullName("Rainhard");
        user.setEmail("rainhard@gmail.com");
        user.setPassword(passwordEncoder.encode("rainhard"));

        List<Role> rainhardRoles = new ArrayList<>();
        rainhardRoles.addAll(List.of(adminRole, userRole, managerRole));
        user.setRoles(rainhardRoles);

        AppUser savedUser = userRepo.save(user);
        log.info("Saved user: {}", savedUser);
//        log.info("User data: {}", user);
    }
}
