package com.auth.jwt.dto.request;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class VerifyOrderRequestDto {

    private String orderNumber;
    private boolean orderedStatus;
}
