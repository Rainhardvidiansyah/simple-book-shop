package com.auth.jwt.seed;

import com.auth.jwt.dto.utils.repository.RoleRepo;
import com.auth.jwt.dto.utils.repository.UserRepo;
import com.auth.jwt.user.AppUser;
import com.auth.jwt.user.ERole;
import com.auth.jwt.user.Role;
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


        var user = new AppUser();
        user.setFullName("Rainhard");
        user.setEmail("rainhard@gmail.com");
        user.setPassword(passwordEncoder.encode("rainhard"));
        List<Role> rainhardRoles = new ArrayList<>();
        rainhardRoles.addAll(List.of(adminRole, userRole, managerRole));
        user.setRoles(rainhardRoles);
        var savedUser = userRepo.save(user);
        log.info("Saved user: {}", savedUser);



        var user2 = new AppUser();
        user2.setFullName("Maulida");
        user2.setEmail("maulida@gmail.com");
        user2.setPassword(passwordEncoder.encode("maulida"));
        List<Role> rolesUser2 = new ArrayList<>();
        rolesUser2.add(userRole);
        user2.setRoles(rolesUser2);
        var savedUser2 = userRepo.save(user2);
        log.info("Saved user 2: {}", savedUser2);

    }
}
