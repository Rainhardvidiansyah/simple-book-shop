package com.auth.jwt.controller;

import com.auth.jwt.activities.BookUploader;
import com.auth.jwt.dto.BooksDto;
import com.auth.jwt.model.Books;
import com.auth.jwt.repository.BookUploaderRepo;
import com.auth.jwt.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BooksController {

    private final BooksService booksService;
    private final BookUploaderRepo uploaderRepo;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save-data")
    public ResponseEntity<?> saveBookData(@RequestBody BooksDto booksDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(booksDto.getTitle().isEmpty()){
            return new ResponseEntity<>("No title", HttpStatus.BAD_REQUEST);
        }
        var sameTitle = booksService.findBooksTitle(booksDto.getTitle());
        if(sameTitle.isPresent()){
            return new ResponseEntity<>("This is not allowed", HttpStatus.BAD_REQUEST);
        }
        Books savedBooks = booksService.saveBooks(Books.saveFromDto(booksDto));
        var uploader = new BookUploader();
        uploader.setEmail(authentication.getName());
        List<String> roles = new ArrayList<>();
        roles.add(String.valueOf(authentication.getAuthorities()));
        uploader.setRoles(roles);
        BookUploader savedUploader = uploaderRepo.save(uploader);
        log.info("Upload data: {}", uploader);
        return new ResponseEntity<>(savedBooks, HttpStatus.OK);
    }

    @GetMapping("/test-string")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public String test(){
        return "Halo";
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String testUser(Authentication authentication){
        return String.format("Hallo %s", authentication.getName());
        //Hit by: /api/books/user?name=namehere
    }


    @GetMapping("/test-user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    private String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return //(UserPrincipal)authentication.getPrincipal();
                String.format("Hello Mr/Mrs %s", authentication.getName());
    }


}
