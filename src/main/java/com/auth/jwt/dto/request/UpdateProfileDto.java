package com.auth.jwt.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class UpdateProfileDto {

    private String email;
    private String password;

}
