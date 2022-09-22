package com.auth.jwt.dto.request;


import lombok.*;

import javax.validation.constraints.NotNull;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class FavoriteRequestDto {

    @NotNull(message = "Please add your shopping items to favorites")
    private Long bookId;
    private String content;


}
