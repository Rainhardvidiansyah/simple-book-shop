package com.auth.jwt.dto.response;

import com.auth.jwt.model.Favorite;
import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor
public class FavoriteResponseInJoin {

    private Long bookId;

    private String title;

    private String author;

    public FavoriteResponseInJoin(Long bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public static FavoriteResponseInJoin From(Favorite favorite){
        return new FavoriteResponseInJoin(favorite.getBook().getId(), favorite.getBook().getTitle(),
                favorite.getBook().getAuthor());
    }

}
