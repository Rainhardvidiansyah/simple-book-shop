package com.auth.jwt.service;

import com.auth.jwt.model.Books;
import com.auth.jwt.repository.BooksRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

class BooksServiceTest {

    private BooksRepo booksRepo;
    @Autowired
    private BooksService booksService = new BooksService(booksRepo);

    private Books books;

    @Test
    void saveBooks(){
       Books books = Books.builder()
                .title("Title 1")
               .synopsis("Synopsis")
               .price("10000")
                .isbn("ISBN")
                .dateOfPublished(new Date().toString())
                .build();
        Books savedBook =
        booksService.saveBooks(books);
        System.out.println(savedBook);
    }


}