package com.auth.jwt.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Appuser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String fullName;

    private String password;

    @Column(length = 150, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public Appuser(Long id, String fullName, String password, String email) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
    }
}
