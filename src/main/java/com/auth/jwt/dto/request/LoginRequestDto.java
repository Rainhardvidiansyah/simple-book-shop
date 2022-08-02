package com.auth.jwt.dto.request;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class LoginRequestDto {

    @NotNull @NotBlank
    private String email;
    @NotNull @NotBlank
    private String password;
}
