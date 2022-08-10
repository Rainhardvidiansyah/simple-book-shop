package com.auth.jwt.model;

import com.auth.jwt.dto.BooksDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder @ToString
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String synopsis;

    private int pages;
    private String price;

    private String paperType;

    private int stocks;

    private String isbn;

    private String dateOfPublished;

    private String dateOfUpload;

    public Books(String title, String synopsis, int pages, String price,
                 String paperType, int stocks, String isbn, String dateOfPublished,
                 String dateOfUpload) {
        this.title = title;
        this.synopsis = synopsis;
        this.pages = pages;
        this.price = price;
        this.paperType = paperType;
        this.stocks = stocks;
        this.isbn = isbn;
        this.dateOfPublished = dateOfPublished;
        this.dateOfUpload = dateOfUpload;
    }

    public static Books saveFromDto(BooksDto booksDto){
        return new Books(booksDto.getTitle(), booksDto.getSynopsis(),
                booksDto.getPages(), booksDto.getPrice(), booksDto.getPaperType(), booksDto.getStocks(),
                booksDto.getIsbn(), booksDto.getDateOfPublished(), booksDto.getDateOfUpload());
    }
}
