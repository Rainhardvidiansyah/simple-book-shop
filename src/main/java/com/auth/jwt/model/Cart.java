package com.auth.jwt.model;


import com.auth.jwt.user.AppUser;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "cart")
@SQLDelete(sql = "UPDATE cart SET deleted = true where id = ?")
@Where(clause = "deleted=false")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private Double price;

    private Double totalPrice;

    private String note;

    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;


}
