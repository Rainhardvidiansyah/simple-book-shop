package com.auth.jwt.model;

import com.auth.jwt.dto.request.FavoriteRequestDto;
import com.auth.jwt.user.AppUser;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

    public Favorite(String content) {
        this.content = content;
    }

    public static Favorite saveFrom(FavoriteRequestDto requestDto){
        return new Favorite(requestDto.getContent());

    }
}
