package com.auth.jwt.dto.request;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class CartRequestDto {

    private Long bookId;

    private int quantity;

    private String note;
}
