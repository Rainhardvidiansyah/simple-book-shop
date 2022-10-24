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
        if(errors.hasErrors()){
            return new ResponseEntity<>(generateFailedResponse(400, "POST", List.of("Please choose one book")), HttpStatus.BAD_REQUEST);
        }
        var email = servletRequest.getUserPrincipal().getName();
        AppUser user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        if(user == null){
            return new ResponseEntity<>(generateFailedResponse(400,"POST", List.of("User is not allowed to do this")), HttpStatus.BAD_REQUEST);
        }
        var savedFavorite = favoriteService.createFavorite(favRequestDto.getBookId(),
                favRequestDto.getContent(), user);
        log.info("Book saved as favorite: {}", savedFavorite.getBook().getTitle());
        return new ResponseEntity<>(generateSuccessResponse(200,"POST",
                new FavoriteResponse(savedFavorite.getContent(), savedFavorite.getBook().getTitle())), HttpStatus.CREATED);
    }

    @GetMapping("/my-favorites")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getUserInFavorite(){
        var email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        var favList = favoriteService.getUserFavorite(user);
        if(favList.isEmpty()){
            return new ResponseEntity<>(generateFailedResponse(404,"GET", List.of("You don't have any favorite")), HttpStatus.NOT_FOUND);
        }
        List<FavoriteResponseInJoin> joinList = new ArrayList<>();
        for(Favorite favorite: favList){
            joinList.add(FavoriteResponseInJoin.From(favorite));
        }
        log.info("List of book: {}", joinList.stream()
                .map(item -> item.getTitle()).collect(Collectors.toList()));
        return new ResponseEntity<>(generateSuccessResponse(200,"GET", joinList), HttpStatus.OK);
    }

    private ResponseMessage<Object> generateSuccessResponse(int code, String method, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(code);
        responseMessage.setMethod(method);
        responseMessage.setMessage(List.of("Success"));
        responseMessage.setData(object);
        return responseMessage;
    }

    private ResponseMessage<Object> generateFailedResponse(String method, List<String> message){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(400);
        responseMessage.setMethod(method);
        responseMessage.setMessage(message);
        responseMessage.setData(null);
        return responseMessage;
    }

    private ResponseMessage<Object> generateFailedResponse(int code, String method, List<String> message){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(code);
        responseMessage.setMethod(method);
        responseMessage.setMessage(message);
        responseMessage.setData(null);
        return responseMessage;
    }





}
