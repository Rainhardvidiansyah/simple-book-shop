package com.auth.jwt.service;


import com.auth.jwt.model.Favorite;
import com.auth.jwt.repository.FavoriteRepo;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepo favoriteRepo;

    public Favorite createFavorite(Favorite favorite, AppUser appUser){
        favorite.setUser(appUser);
        return favoriteRepo.save(favorite);
    }
}
