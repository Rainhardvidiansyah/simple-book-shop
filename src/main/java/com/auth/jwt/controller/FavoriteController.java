package com.auth.jwt.controller;

import com.auth.jwt.dto.request.FavoriteRequestDto;
import com.auth.jwt.dto.response.FavoriteResponse;
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

@RestController
@RequestMapping("/api/v1/favorite")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserRepo userRepo;
    private final BooksRepo booksRepo;
    private final HttpServletRequest request;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> makeFavorite(@RequestBody FavoriteRequestDto favoriteRequestDto){
        String email = request.getUserPrincipal().getName();
        AppUser user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        var savedFavorite =
        favoriteService.createFavorite(favoriteRequestDto.getBookId(), user);
        log.info("Data saved: {}", savedFavorite);
        return new ResponseEntity<>(new FavoriteResponse(savedFavorite.getContent(), savedFavorite.getBook().getTitle(),
                user.getFullName()), HttpStatus.OK);
    }


}
