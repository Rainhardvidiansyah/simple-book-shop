package com.auth.jwt.dto.response;

import com.auth.jwt.model.BookImage;
import com.auth.jwt.model.Favorite;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @NoArgsConstructor
public class FavoriteResponseInJoin {

    private Long bookId;

    private String title;

    private String author;

    private List <byte[]> image_data = new ArrayList<>();


    public FavoriteResponseInJoin(Long bookId, String title, String author, List <byte[]> image_data) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.image_data = image_data;
    }

    public static FavoriteResponseInJoin From(Favorite favorite){
        return new FavoriteResponseInJoin(favorite.getBook().getId(), favorite.getBook().getTitle(),
                favorite.getBook().getAuthor(),
                favorite.getBook().getBookImage().stream().map(item -> item.getImageData()).collect(Collectors.toList()));
    }

}
