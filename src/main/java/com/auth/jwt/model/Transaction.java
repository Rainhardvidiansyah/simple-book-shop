package com.auth.jwt.model;

import com.auth.jwt.user.AppUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(unique = true)
    private String orderNumber;

    private String senderAccountNumber;

    private String senderBank;

    private double totalPrice;

    private Date date;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @JsonBackReference
    @OneToOne(mappedBy = "transaction")
    private Order order;



}
