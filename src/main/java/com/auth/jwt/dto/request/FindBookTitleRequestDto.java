package com.auth.jwt.dto.request;


import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class FindBookTitleRequestDto {

    private String title;
}
