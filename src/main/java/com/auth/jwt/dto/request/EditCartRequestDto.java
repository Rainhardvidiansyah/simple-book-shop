package com.auth.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor @Getter @Setter
public class EditCartRequestDto {

    private Long cartId;
    private int quantity;
    private String note;

}
