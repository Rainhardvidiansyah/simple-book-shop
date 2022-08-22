package com.auth.jwt.model;


import com.auth.jwt.user.AppUser;
import lombok.*;

import javax.persistence.*;


@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private String price;

    private String totalPrice;

    private String note;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;


}
