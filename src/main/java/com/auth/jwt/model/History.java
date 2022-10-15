package com.auth.jwt.model;


import com.auth.jwt.user.AppUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private AppUser user;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonBackReference
    private Order order;



}
