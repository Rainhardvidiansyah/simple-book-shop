package com.auth.jwt.service;



import com.auth.jwt.model.Favorite;
import com.auth.jwt.repository.BooksRepo;
import com.auth.jwt.repository.FavoriteRepo;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepo favoriteRepo;
    private final BooksRepo booksRepo;

    public Favorite createFavorite(Long bookId, AppUser user){
        var book = booksRepo.findById(bookId)
                .orElseThrow(RuntimeException::new);
        var favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBook(book);
        return favoriteRepo.save(favorite);
    }

    public List<Favorite> getUser(AppUser user){
        return favoriteRepo.findFavoriteByUser(user);
    }







}
