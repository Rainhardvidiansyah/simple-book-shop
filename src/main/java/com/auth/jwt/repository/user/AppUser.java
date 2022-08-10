package com.auth.jwt.repository.user;

import com.auth.jwt.dto.request.RegistrationRequest;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity @Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String fullName;

    @Column(length = 150, nullable = false)
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public AppUser(String fullName, String password, String email, List<Role> roles) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public static AppUser createUserFrom(RegistrationRequest request){
        AppUser user = new AppUser();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }
}
