package com.auth.jwt.dto.response;

import com.auth.jwt.model.BookImage;
import com.auth.jwt.model.Favorite;
import lombok.*;

import java.util.List;

@Getter @Setter @ToString @NoArgsConstructor
public class FavoriteResponseInJoin {

    private Long bookId;

    private String title;

    private String author;

    private List<BookImage> picture;


    public FavoriteResponseInJoin(Long bookId, String title, String author, List <BookImage> bookImages) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.picture = bookImages;
    }

    public static FavoriteResponseInJoin From(Favorite favorite){
        return new FavoriteResponseInJoin(favorite.getBook().getId(), favorite.getBook().getTitle(),
                favorite.getBook().getAuthor(), favorite.getBook().getBookImage());
    }

}
