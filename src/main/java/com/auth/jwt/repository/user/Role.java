package com.auth.jwt.repository.user;

import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "role")
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor @ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole roleName;

    public Role(ERole roleName) {
        this.roleName = roleName;
    }
}
