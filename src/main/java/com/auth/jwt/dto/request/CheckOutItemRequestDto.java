package com.auth.jwt.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @ToString
public class CheckOutItemRequestDto {

    private String productName;
    private double price;
    private int quantity;
    private Long productId;
    private Long userId;



}
