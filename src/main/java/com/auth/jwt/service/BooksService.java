package com.auth.jwt.service;

import com.auth.jwt.dto.BooksDto;
import com.auth.jwt.model.Book;
import com.auth.jwt.repository.BooksRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BooksService {


    private final BooksRepo booksRepo;

    private BigDecimal setPrice(String price){
        BigDecimal validAmount = new BigDecimal(price);
        return validAmount;
    }

    private String convertPrice(String price){
        BigDecimal bigDecimal = new BigDecimal(price);
        return bigDecimal.toString();
    }

    private static String setDate(){
        Date dateNow = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
        var setDateFormat = simpleDateFormat.format(dateNow);
        return setDateFormat;
    }

    public Book saveBooks(Book book){
        book.setPrice(convertPrice(book.getPrice()));
        book.setDateOfUpload(setDate());
        return booksRepo.save(book);
    }

    public Optional<Book> findBookTitle(String title){
        return booksRepo.findBooksByTitle(title);
    }

    public List<Book> findAllBooks(){
        return booksRepo.findAll();
    }

    public List<Book> findBooksByAuthorName(String authorName){
        return booksRepo.findByAuthorOrderByTitleAsc(authorName);
    }

    public Optional<Book> findBookId(Long id){
        return booksRepo.findById(id);
    }

    public Book updateBook(Long id, Book book){
        Book updateBook = booksRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Book with %d not found", id)));
        updateBook.setTitle(book.getTitle());
        updateBook.setAuthor(book.getAuthor());
        updateBook.setSynopsis(book.getSynopsis());
        updateBook.setPages(book.getPages());
        updateBook.setPrice(book.getPrice());
        updateBook.setPaperType(book.getPaperType());
        updateBook.setStocks(book.getStocks());
        updateBook.setIsbn(book.getIsbn());
        updateBook.setDateOfPublished(book.getDateOfPublished());
        updateBook.setDateOfUpload(book.getDateOfUpload());
        updateBook.setDateOfUpload(setDate());
        return booksRepo.save(updateBook);
    }

    public void deleteBook(Long id){
        booksRepo.deleteById(id);
    }
}
