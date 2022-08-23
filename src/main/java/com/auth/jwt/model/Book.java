package com.auth.jwt.model;

import com.auth.jwt.dto.BooksDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.type.AnyType;

import javax.persistence.*;
import java.util.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder @ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String synopsis;
    private String tags;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "book_category",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") })
    private Set<Category> categories = new HashSet<>();


    @JsonManagedReference
    @OneToMany(targetEntity = BookImage.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private List<BookImage> bookImage = new ArrayList<>();

    private int pages;
    private Double price;
    private String paperType;
    private int stocks;
    private String isbn;
    private String dateOfPublished;
    private String dateOfUpload;

    public Book(String title, String author, String synopsis, String tags,Set<Category> categories,
                int pages, Double price, String paperType, int stocks, String isbn,
                String dateOfPublished, String dateOfUpload) {
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
        this.tags = tags;
        this.categories = categories;
        this.pages = pages;
        this.price = price;
        this.paperType = paperType;
        this.stocks = stocks;
        this.isbn = isbn;
        this.dateOfPublished = dateOfPublished;
        this.dateOfUpload = dateOfUpload;
    }

    public static Book saveFromDto(BooksDto booksDto){
        return new Book(booksDto.getTitle(), booksDto.getAuthorName(),
                booksDto.getSynopsis(), booksDto.getTags(),
                booksDto.getCategories(), booksDto.getPages(), booksDto.getPrice(),
                booksDto.getPaperType(), booksDto.getStocks(), booksDto.getIsbn(),
                booksDto.getDateOfPublished(), booksDto.getDateOfUpload());
    }


}
