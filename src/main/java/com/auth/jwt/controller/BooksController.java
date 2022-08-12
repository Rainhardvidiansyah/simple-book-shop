package com.auth.jwt.controller;

import com.auth.jwt.activities.BookUploader;
import com.auth.jwt.dto.BooksDto;
import com.auth.jwt.dto.request.FindAuthorAndBooksRequest;
import com.auth.jwt.dto.response.FindAuthorAndBooksResponse;
import com.auth.jwt.model.Book;
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
import java.util.stream.Collectors;


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
            return new ResponseEntity<>("Book is present!!", HttpStatus.BAD_REQUEST);
        }
        Book savedBook = booksService.saveBooks(Book.saveFromDto(booksDto));
        log.info("{}", savedBook);
        var uploader = new BookUploader();
        uploader.setEmail(authentication.getName());
        List<String> roles = new ArrayList<>();
        roles.add(String.valueOf(authentication.getAuthorities()));
        uploader.setRoles(roles);
        BookUploader savedUploader = uploaderRepo.save(uploader);
        log.info("Upload data: {}", uploader);
        return new ResponseEntity<>(savedBook, HttpStatus.OK);
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

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAllBooks(){
        List<Book> books = booksService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findTitleByAuthor(@RequestBody FindAuthorAndBooksRequest request){
        List<Book> findTitles = booksService.findBooksByAuthorName(request.getAuthorName());
        List<FindAuthorAndBooksResponse> responseList = new ArrayList<>();
        for(Book book : findTitles){
            var response = new FindAuthorAndBooksResponse();
            response.setBookTitle(book.getTitle());
            response.setAuthor(book.getAuthor());
            response.setSynopsis(book.getSynopsis());
            response.setPages(book.getPages());
            response.setPrice(book.getPrice());
            response.setPaperType(book.getPaperType());
            response.setStocks(book.getStocks());
            response.setIsbn(book.getIsbn());
            responseList.add(response);
        }
        if(findTitles.isEmpty()){
            return new ResponseEntity<>("Books not found", HttpStatus.OK);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }



}
