package com.auth.jwt.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor @ToString
public class TokenRequestDto {

    @NotBlank
    private String refreshToken;
}
