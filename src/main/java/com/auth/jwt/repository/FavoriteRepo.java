package com.auth.jwt.repository;

import com.auth.jwt.dto.response.FavoriteResponseInJoin;
import com.auth.jwt.model.Favorite;
import com.auth.jwt.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite, Long> {

    @Query(
    value = "select * from favorite f where join user on (favorite.id_user = user.id)", nativeQuery=true)
    List<Favorite> getUser(AppUser user);

    List<Favorite> findFavoriteByUser(AppUser user);

}
