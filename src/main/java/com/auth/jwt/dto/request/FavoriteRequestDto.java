package com.auth.jwt.dto.request;


import lombok.*;

@NoArgsConstructor
@Getter @Setter @ToString
public class FavoriteRequestDto {

    private String content;
    private Long bookId;

    public FavoriteRequestDto(String content, Long bookId) {
        this.content = content;
        this.bookId = bookId;
    }

}
