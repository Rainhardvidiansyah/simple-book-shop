package com.auth.jwt.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class OrderRequestDto {

    @NotBlank(message = "This payment method cannot be empty!")
    private String paymentMethod;
}
