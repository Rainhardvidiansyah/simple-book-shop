package com.auth.jwt.dto.request;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class AddressRequestDto {


    @NotBlank(message = "Address cannot be blank")
    @Length(min = 20, max = 500)
    private String completeAddress;

    @NotBlank(message = "Phone number cannot be empty")
    @Length(min = 11, max = 12)
    private String phoneNumber;

    @NotBlank(message = "Please write your postal code properly!")
    @Length(min = 5, max = 10)
    private String postalCode;
}
