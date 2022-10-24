package com.auth.jwt.dto.request;


import com.auth.jwt.validator.password.MatchPasswordAnnotation;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
@MatchPasswordAnnotation(message = "Passwords don't match!")
public class RegistrationRequest {

    @NotBlank(message = "Name cannot be blank")
    @Length(min = 5, max = 512, message = "User name must be between 5-512 characters")
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    @Length(min = 5, max = 512, message = "Email must be between 5-512 characters")
    public String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private String matchPassword;

}
