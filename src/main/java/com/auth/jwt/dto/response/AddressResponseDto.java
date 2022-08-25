package com.auth.jwt.dto.response;

import com.auth.jwt.model.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
public class AddressResponseDto {

    private String completeAddress;
    private String phoneNumber;
    private String postalCode;

    public AddressResponseDto(String completeAddress,
                              String phoneNumber, String postalCode) {
        this.completeAddress = completeAddress;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
    }

    public static AddressResponseDto From(Address address){
        return new AddressResponseDto(address.getCompleteAddress(),
                address.getPhoneNumber(), address.getPostalCode());
    }
}
