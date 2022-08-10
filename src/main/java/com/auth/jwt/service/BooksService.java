package com.auth.jwt.service;

import com.auth.jwt.model.Books;
import com.auth.jwt.repository.BooksRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public Books saveBooks(Books books){
        books.setPrice(convertPrice(books.getPrice()));
        books.setDateOfUpload(setDate());
        return booksRepo.save(books);
    }

    public Optional<Books> findBooksTitle(String title){
        return booksRepo.findBooksByTitle(title);
    }
}
