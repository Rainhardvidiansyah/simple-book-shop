package com.auth.jwt.controller;

import com.auth.jwt.activities.BookUploader;
import com.auth.jwt.dto.BooksDto;
import com.auth.jwt.dto.request.FindAuthorAndBooksRequest;
import com.auth.jwt.dto.response.BookResponse;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/book")
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
            return new ResponseEntity<>("Title must be written!", HttpStatus.BAD_REQUEST);
        }
        var sameTitle = booksService.findBookTitle(booksDto.getTitle());
        if(sameTitle.isPresent()){
            return new ResponseEntity<>("Book is present!!", HttpStatus.BAD_REQUEST);
        }
        Book savedBook = booksService.saveBooks(Book.saveFromDto(booksDto));
        log.info("{}", savedBook);
        var uploader = new BookUploader();
        uploader.setEmail(authentication.getName());
        uploader.setBookId(savedBook.getId());
        List<String> roles = new ArrayList<>();
        roles.add(String.valueOf(authentication.getAuthorities()));
        uploader.setRoles(roles);
        BookUploader savedUploader = uploaderRepo.save(uploader);
        log.info("Upload data: {}", savedUploader);
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
    }


    @GetMapping("/test-user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    private String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return String.format("Hello Mr/Mrs %s", authentication.getName());
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAllBooks(){
        var bookList = booksService.findAllBooks();
        List<BookResponse> responses = new ArrayList<>();
        for(Book book: bookList){
            responses.add(BookResponse.From(book));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public ResponseEntity<?> findTitleByAuthor(@RequestBody FindAuthorAndBooksRequest request){
        List<Book> findTitles = booksService.findBooksByAuthorName(request.getAuthorName());
        if(findTitles.isEmpty()){
            return new ResponseEntity<>("Books not found", HttpStatus.OK);
        }
        List<BookResponse> responses = new ArrayList<>();
        for(Book book: findTitles){
            responses.add(BookResponse.From(book));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping()
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public ResponseEntity<?> findBookId(@RequestParam Long data_id){
        var book = booksService.findBookId(data_id);
        if(book.isEmpty()){
            assert HttpStatus.resolve(201) != null;
            return new ResponseEntity<>("Book not Found", HttpStatus.resolve(201));
        }
            return new ResponseEntity<>(BookResponse.From(book.get()), HttpStatus.OK);
        }

    @PutMapping("/update") // /update/?data_id=1
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateBook(@RequestParam Long data_id,
                                        @RequestBody BooksDto booksDto){
        if(booksDto.getTitle().isEmpty()){
            return new ResponseEntity<>("Title must be written!", HttpStatus.BAD_REQUEST);
        }
        var book = booksService.findBookId(data_id);
        if(book.isEmpty()){
            assert HttpStatus.resolve(201) != null;
            return new ResponseEntity<>("Book not Found", HttpStatus.resolve(201));
        }
        var updatedBook = booksService.updateBook(data_id, Book.saveFromDto(booksDto));
        log.info("Updated {}", updatedBook);
        return new ResponseEntity<>(BookResponse.From(updatedBook), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{data_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable("data_id") Long id){
        var book = booksService.findBookId(id);
        booksService.deleteBook(book.get().getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }







}
