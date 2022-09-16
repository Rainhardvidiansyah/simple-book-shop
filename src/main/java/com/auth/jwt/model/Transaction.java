package com.auth.jwt.model;

import com.auth.jwt.user.AppUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod;

    private String orderNumber;

    private double totalPrice;

    private Date date;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @OneToOne(mappedBy = "transaction")
    private Order order;



}
