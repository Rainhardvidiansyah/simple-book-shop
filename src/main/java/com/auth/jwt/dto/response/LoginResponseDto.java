package com.auth.jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class LoginResponseDto {

    private String email;
    private String jwtToken;
    private String refreshToken;
    private List<String> roles;

    public static LoginResponseDto userData(String email, String jwtToken,
                                            String refreshToken, List<String> roles){
        return new LoginResponseDto(email, jwtToken, refreshToken, roles);
    }


}
