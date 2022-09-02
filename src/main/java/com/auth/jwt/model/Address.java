package com.auth.jwt.model;

import com.auth.jwt.dto.request.AddressRequestDto;
import com.auth.jwt.user.AppUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter @ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500)
    private String completeAddress;
    private String phoneNumber;
    private String postalCode;

    //@JsonBackReference
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    public Address(String completeAddress, String phoneNumber, String postalCode) {
        this.completeAddress = completeAddress;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
    }

    public Address(String completeAddress, String phoneNumber, String postalCode, AppUser user) {
        this.completeAddress = completeAddress;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.user = user;
    }

    public static Address From(AddressRequestDto addressDto){
        return new Address(addressDto.getCompleteAddress(), addressDto.getPhoneNumber(),
                addressDto.getPostalCode());
    }
}
