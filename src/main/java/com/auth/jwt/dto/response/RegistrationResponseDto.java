package com.auth.jwt.dto.response;

import com.auth.jwt.repository.user.AppUser;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistrationResponseDto {

    private String fullName;
    private String email;

    public RegistrationResponseDto(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public static RegistrationResponseDto registrationResponse(AppUser user){
        return new RegistrationResponseDto(user.getFullName(), user.getEmail());
    }
}
