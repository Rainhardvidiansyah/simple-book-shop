package com.auth.jwt.controller;

import com.auth.jwt.dto.request.FavoriteRequestDto;
import com.auth.jwt.dto.response.FavoriteResponse;
import com.auth.jwt.dto.response.FavoriteResponseInJoin;
import com.auth.jwt.dto.response.ResponseMessage;
import com.auth.jwt.dto.utils.ErrorUtils;
import com.auth.jwt.model.Favorite;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.service.FavoriteService;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/favorite")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserRepo userRepo;
    private final HttpServletRequest servletRequest;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> makeFavorite(@Valid @RequestBody FavoriteRequestDto favRequestDto, Errors errors){
        ResponseMessage<FavoriteResponse> responseMessage = new ResponseMessage<>();
        if(errors.hasErrors()){
            responseMessage.setCode(400);
            responseMessage.setMessage(List.of("Error is happening"));
            responseMessage.setMessage(ErrorUtils.err(errors));
            responseMessage.setData(null);
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
        var email = servletRequest.getUserPrincipal().getName();
        AppUser user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        if(user == null){
            responseMessage.setCode(400);
            responseMessage.setMessage(List.of("User is not allowed to do this"));
            responseMessage.setData(null);
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
        var savedFavorite = favoriteService.createFavorite(favRequestDto.getBookId(),
                favRequestDto.getContent(), user);
        log.info("Book saved as favorite: {}", savedFavorite.getBook().getTitle());
        responseMessage.setCode(200);
        responseMessage.setMessage(List.of("Success"));
        responseMessage.setData(new FavoriteResponse(savedFavorite.getContent(), savedFavorite.getBook().getTitle()));
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/my-favorites")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getUserInFavorite(){
        ResponseMessage<String> responseMessage = new ResponseMessage<>();
        var email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        log.info(servletRequest.getHeader("What's this? "+ user.getEmail()));

        var favList = favoriteService.getUserFavorite(user);

        if(favList.isEmpty()){
            responseMessage.setCode(400);
            responseMessage.setMessage(List.of("You don't have any favorite"));
            responseMessage.setData(null);
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        List<FavoriteResponseInJoin> joinList = new ArrayList<>();
        for(Favorite favorite: favList){
            joinList.add(FavoriteResponseInJoin.From(favorite));
        }

        List<String> bookData = joinList.stream()
                        .map(FavoriteResponseInJoin::getTitle).collect(Collectors.toList());
        log.info("List of book: {}", bookData);

        responseMessage.setCode(200);
        responseMessage.setMessage(List.of("Success"));
        responseMessage.setData(String.valueOf(bookData));
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }





}
