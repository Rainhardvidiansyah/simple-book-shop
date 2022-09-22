package com.auth.jwt.controller;

import com.auth.jwt.dto.request.FavoriteRequestDto;
import com.auth.jwt.dto.response.FavoriteResponse;
import com.auth.jwt.dto.response.FavoriteResponseInJoin;
import com.auth.jwt.model.Favorite;
import com.auth.jwt.repository.BooksRepo;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.service.FavoriteService;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/favorite")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserRepo userRepo;
    private final BooksRepo booksRepo;
    private final HttpServletRequest servletRequest;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> makeFavorite(@RequestBody FavoriteRequestDto favRequestDto){
        var email = servletRequest.getUserPrincipal().getName();
        AppUser user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        var savedFavorite = favoriteService.createFavorite(favRequestDto.getBookId(), user);
        log.info("Book saved as favorite: {}", savedFavorite.getBook().getTitle());
        return new ResponseEntity<>(new FavoriteResponse(savedFavorite.getContent(), savedFavorite.getBook().getTitle(),
                user.getFullName()), HttpStatus.OK);
    }

    @GetMapping("/my-favorites")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserInFavorite(){
        var email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        log.info(servletRequest.getHeader(user.getEmail()));

        var favList = favoriteService.getUser(user);
        List<FavoriteResponseInJoin> joinList = new ArrayList<>();
        for(Favorite favorite: favList){
            joinList.add(FavoriteResponseInJoin.From(favorite));
        }
        return new ResponseEntity<>(joinList, HttpStatus.OK);
    }





}
