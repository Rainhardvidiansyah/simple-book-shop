package com.auth.jwt.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class RegistrationRequest {

    private String fullName;
    public String email;
    private String password;
    //private String matchPassword;
    private List<String> authority;
}
