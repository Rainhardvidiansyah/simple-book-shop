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
        var responseMessage = new ResponseMessage<Object>();
        if(errors.hasErrors()){
            return new ResponseEntity<>(generateFailedResponse(List.of("Error is happening")), HttpStatus.BAD_REQUEST);
        }
        var email = servletRequest.getUserPrincipal().getName();
        AppUser user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        if(user == null){
            return new ResponseEntity<>(generateFailedResponse(List.of("User is not allowed to do this")), HttpStatus.BAD_REQUEST);
        }
        var savedFavorite = favoriteService.createFavorite(favRequestDto.getBookId(),
                favRequestDto.getContent(), user);
        log.info("Book saved as favorite: {}", savedFavorite.getBook().getTitle());
        return new ResponseEntity<>(generateSuccessResponse("POST",
                new FavoriteResponse(savedFavorite.getContent(), savedFavorite.getBook().getTitle())), HttpStatus.OK);
    }

    @GetMapping("/my-favorites")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getUserInFavorite(){
        var responseMessage = new ResponseMessage<Object>();
        var email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);

        var favList = favoriteService.getUserFavorite(user);
        if(favList.isEmpty()){
            return new ResponseEntity<>(generateFailedResponse(List.of("You don't have any favorite")), HttpStatus.BAD_REQUEST);
        }
        List<FavoriteResponseInJoin> joinList = new ArrayList<>();
        for(Favorite favorite: favList){
            joinList.add(FavoriteResponseInJoin.From(favorite));
        }
        List<String> bookData = joinList.stream()
                        .map(FavoriteResponseInJoin::getTitle).collect(Collectors.toList());
        log.info("List of book: {}", bookData);
        return new ResponseEntity<>(generateSuccessResponse("GET", joinList), HttpStatus.OK);
    }

    private ResponseMessage<Object> generateSuccessResponse(String method, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(200);
        responseMessage.setMethod(method);
        responseMessage.setMessage(List.of("Success"));
        responseMessage.setData(object);
        return responseMessage;
    }

    private ResponseMessage<Object> generateFailedResponse(List<String> message){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(400);
        responseMessage.setMethod(null);
        responseMessage.setMessage(message);
        responseMessage.setData(null);
        return responseMessage;
    }





}
