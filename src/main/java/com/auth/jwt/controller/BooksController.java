package com.auth.jwt.controller;

import com.auth.jwt.activities.BookUploader;
import com.auth.jwt.dto.request.BooksDtoRequest;
import com.auth.jwt.dto.request.FindAuthorAndBooksRequest;
import com.auth.jwt.dto.request.FindBooksTagRequestDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@Slf4j
public class BooksController {

    private final BooksService booksService;
    private final BookUploaderRepo uploaderRepo;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> saveBookData(@RequestBody BooksDtoRequest booksDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(booksDto.getTitle().isEmpty() && booksDto.getTitle().isBlank()){
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
        log.info("Upload book: {}", savedUploader);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public ResponseEntity<?> findAllBooks(){
        var bookList = booksService.findAllBooks();
        return new ResponseEntity<>(responses(bookList), HttpStatus.OK);
    }

    @PostMapping("/search/author")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public ResponseEntity<?> findTitleByAuthor(@RequestBody FindAuthorAndBooksRequest request){
        var bookList = booksService.findBooksByAuthorName(request.getAuthorName());
        if(bookList.isEmpty()){
            return new ResponseEntity<>("Books not found", HttpStatus.OK);
        }
        List<BookResponse> responses = new ArrayList<>();
        for(Book book: bookList){
            responses.add(BookResponse.From(book));
        }
        return new ResponseEntity<>(responses(bookList), HttpStatus.OK);
    }

    @GetMapping()
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public ResponseEntity<?> findBookId(@RequestParam Long data_id){
        var book = booksService.findBookId(data_id);
        if(book.isEmpty()){
            return new ResponseEntity<>("Book not Found", HttpStatus.BAD_REQUEST);
        }
            return new ResponseEntity<>(BookResponse.From(book.get()), HttpStatus.OK);
        }

    @PutMapping("/update")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateBook(@RequestParam Long id,
                                        @RequestBody BooksDtoRequest booksDto){
        if(booksDto.getTitle().isEmpty()){
            return new ResponseEntity<>("Title must be written!", HttpStatus.BAD_REQUEST);
        }
        var book = booksService.findBookId(id);
        if(book.isEmpty()){
            return new ResponseEntity<>("Book not Found", HttpStatus.BAD_REQUEST);
        }
        var updatedBook = booksService.updateBook(id, Book.saveFromDto(booksDto));
        log.info("Updated {}", updatedBook);
        return new ResponseEntity<>(BookResponse.From(updatedBook), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable("id") Long id){
        var book = booksService.findBookId(id);
        booksService.deleteBook(book.get().getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search/tags")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> findTagsBook(@RequestBody FindBooksTagRequestDto requestDto){
        var bookList = booksService.findBooksByTags(requestDto.getTags());
        List<BookResponse> responses = new ArrayList<>();
        for(Book book:bookList){
            responses.add(BookResponse.From(book));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/amount")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getBookAmount() {
        var bookList = booksService.findAllBooks();
        int sizeAMount = bookList.size();
        List<BookResponse> responses = new ArrayList<>();
        for(Book book: bookList){
            responses.add(BookResponse.From(book));
        }
            return new ResponseEntity<>(sizeAMount, HttpStatus.OK);
    }

    private List<BookResponse> responses(List<Book> bookLists){
        bookLists = booksService.findAllBooks();
        List<BookResponse> responses = new ArrayList<>();
        for(Book book: bookLists){
            responses.add(BookResponse.From(book));
        }
        return responses;
    }

    @PostMapping("/{book_id}/add-image")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> storeImageForBook(@PathVariable("book_id") Long bookId,
                                               @RequestParam("file") MultipartFile file){
        var savedImagesInBook = booksService.insertImage(bookId, file);
        if(file.isEmpty() && bookId == null){
            return new ResponseEntity<>("Sorry data can't be processed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedImagesInBook, HttpStatus.OK);
    }

    @PostMapping("/{book_id}/add-images")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addImages(@PathVariable("book_id") Long bookId,
                                      @RequestParam("file") MultipartFile[] file){
        Map<String, Boolean> failedResponse = new HashMap<>();
        failedResponse.put("Failed", Boolean.FALSE);
        if(file == null && bookId == null){
            return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
        }
        booksService.addAnotherImage(bookId, file);
        Map<String, Boolean> successfulResponse = new HashMap<>();
        successfulResponse.put("Succeeded", Boolean.TRUE);
        return new ResponseEntity<>(successfulResponse, HttpStatus.OK);
    }

}
