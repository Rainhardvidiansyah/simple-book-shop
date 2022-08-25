package com.auth.jwt.dto.request;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class AddressRequestDto {


    @NotBlank(message = "Address cannot be blank")
    @Length(min = 20, max = 500, message = "Address must be between 20 = 500 words")
    private String completeAddress;

    @NotBlank(message = "Phone number cannot be empty")
    @Length(min = 11, max = 12, message = "Phone number must be either 11 or 12 words")
    private String phoneNumber;

    @NotBlank(message = "Please write your postal code properly!")
    @Length(min = 5, max = 7, message = "Postal code must be either 5 or 7 words")
    private String postalCode;
}
