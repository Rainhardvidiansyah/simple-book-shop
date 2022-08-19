package com.auth.jwt.dto.response;

import com.auth.jwt.model.Favorite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class FavoriteResponse {


    private String content;

    private Long userId;

    private String userName;

    public static FavoriteResponse From(Favorite favorite){
        return new FavoriteResponse(favorite.getContent(), favorite.getUser().getId(),
                favorite.getUser().getFullName());
    }


}
