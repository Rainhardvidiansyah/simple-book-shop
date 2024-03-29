package com.auth.jwt.dto.response;

import com.auth.jwt.user.AppUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class RegistrationResponseDto {

    private String fullName;
    private String email;

    public RegistrationResponseDto(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public static RegistrationResponseDto From(AppUser user){
        return new RegistrationResponseDto(user.getFullName(), user.getEmail());
    }
}
