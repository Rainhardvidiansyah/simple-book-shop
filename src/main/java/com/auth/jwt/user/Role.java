package com.auth.jwt.user;

import javax.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    @Enumerated(EnumType.STRING)
    private ERole eRole;
}
