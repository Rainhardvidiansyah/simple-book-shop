package com.auth.jwt.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class OrderRequestDto {

    @NotBlank(message = "This payment method cannot be empty!")
    @Length(min = 3, max = 20, message = "Input your method payment")
    private String paymentMethod;
}
