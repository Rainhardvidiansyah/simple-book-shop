package com.auth.jwt.dto.request;


import com.auth.jwt.user.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter @ToString
public class UpdateProfileDto {

    private String email;
    private String fullName;


    public UpdateProfileDto(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public static UpdateProfileDto From(AppUser user){
        return new UpdateProfileDto(user.getEmail(), user.getFullName());
    }
}
