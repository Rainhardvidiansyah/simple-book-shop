package com.auth.jwt.service;

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
        return booksRepo.save(book);
    }

    public Optional<Book> findBooksTitle(String title){
        return booksRepo.findBooksByTitle(title);
    }

    public List<Book> findAllBooks(){
        return booksRepo.findAll();
    }

    public List<Book> findBooksByAuthorName(String authorName){
        return booksRepo.findByAuthorOrderByTitleAsc(authorName);
    }
}
