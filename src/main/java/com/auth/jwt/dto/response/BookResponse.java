package com.auth.jwt.dto.response;

import com.auth.jwt.model.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;


@NoArgsConstructor
@Getter @Setter
public class BookResponse {

    private String bookTitle;
    private String author;
    private String synopsis;
    private String tags;
    private int pages;
    private Double price;
    private String paperType;
    private int stocks;
    private String isbn;
    private String dateOfPublished;
    private String dateOfUpload;
    private List<byte[]>  imageData;

    public BookResponse(String bookTitle, String author, String synopsis, String tags, int pages,
                        Double price, String paperType, int stocks, String isbn,
                        String dateOfPublished, String dateOfUpload, List<byte[]>  imageData) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.synopsis = synopsis;
        this.tags = tags;
        this.pages = pages;
        this.price = price;
        this.paperType = paperType;
        this.stocks = stocks;
        this.isbn = isbn;
        this.dateOfPublished = dateOfPublished;
        this.dateOfUpload = dateOfUpload;
        this.imageData = imageData;
    }

    public static BookResponse From(Book book) {
        return new BookResponse(
                book.getTitle(), book.getAuthor(), book.getSynopsis(), book.getTags(), book.getPages(),
                book.getPrice(), book.getPaperType(), book.getStocks(),
                book.getIsbn(), book.getDateOfPublished(), book.getDateOfUpload(),
                book.getBookImage().stream().map(item -> item.getImageData()).collect(Collectors.toList())
                );
    }
}
