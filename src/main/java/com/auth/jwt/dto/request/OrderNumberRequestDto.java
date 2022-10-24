package com.auth.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class OrderNumberRequestDto {

    private String orderNumber;
}
