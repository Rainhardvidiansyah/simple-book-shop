package com.auth.jwt.dto.response;

import com.auth.jwt.model.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter @Setter
public class BookResponse {

    private String bookTitle;
    private String author;
    private String synopsis;
    private int pages;
    private String price;
    private String paperType;
    private int stocks;
    private String isbn;
    private String dateOfPublished;
    private String dateOfUpload;

    public BookResponse(String bookTitle, String author, String synopsis, int pages,
                        String price, String paperType, int stocks, String isbn,
                        String dateOfPublished, String dateOfUpload) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.synopsis = synopsis;
        this.pages = pages;
        this.price = price;
        this.paperType = paperType;
        this.stocks = stocks;
        this.isbn = isbn;
        this.dateOfPublished = dateOfPublished;
        this.dateOfUpload = dateOfUpload;
    }

    public static BookResponse From(Book book) {
        return new BookResponse(
                book.getTitle(), book.getAuthor(), book.getSynopsis(), book.getPages(),
                book.getPrice(), book.getPaperType(), book.getStocks(),
                book.getIsbn(), book.getDateOfPublished(), book.getDateOfUpload());
    }
}
