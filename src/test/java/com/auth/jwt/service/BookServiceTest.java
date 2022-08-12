package com.auth.jwt.service;

import com.auth.jwt.model.Book;
import com.auth.jwt.repository.BooksRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BooksRepo booksRepo;
    @Autowired @InjectMocks
    private BooksService booksService = new BooksService(booksRepo);

    private Book book;

    @Test
    void saveBooks(){

    }


}