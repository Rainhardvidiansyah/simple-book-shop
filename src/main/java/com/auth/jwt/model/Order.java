package com.auth.jwt.model;

import com.auth.jwt.user.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "order_master")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "session_id")
    private String sessionId;

    private boolean ordered;

    private String payment_method;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;



}
