package com.auth.jwt.dto.response;


import lombok.*;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class CartResponseForUser {

    List<CartResponse> cartResponses;
    Double totalCost;
}
