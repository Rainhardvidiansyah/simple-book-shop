package com.auth.jwt.security.jwt;

import com.auth.jwt.user.AppUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "refreshtoken")
@Getter @Setter
public class RefreshToken {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private AppUser user;
        @Column(nullable = false, unique = true)
        private String token;
        @Column(nullable = false)
        private Instant expiryDate;
}
