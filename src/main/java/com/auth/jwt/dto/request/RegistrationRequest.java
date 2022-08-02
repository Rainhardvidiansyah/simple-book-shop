package com.auth.jwt.dto.request;


import com.auth.jwt.validator.registation.DataUserConstraint;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter @DataUserConstraint
public class RegistrationRequest {

    private String fullName;
    public String email;
    private String password;
    //private String matchPassword;
    private List<String> authority;
}
