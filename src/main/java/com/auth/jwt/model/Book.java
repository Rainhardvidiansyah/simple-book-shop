package com.auth.jwt.model;

import com.auth.jwt.dto.request.BooksRequestDto;
import com.auth.jwt.helper.BaseAuditing;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "book")
@SQLDelete(sql = "UPDATE book SET deleted = true where id = ?")
@Where(clause = "deleted = false")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder @ToString
public class Book  extends BaseAuditing<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    @Column(length = 500)
    private String synopsis;
    private String tags;

    private boolean deleted = Boolean.FALSE;

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
    @OneToMany(mappedBy = "book")
    private List<BookImage> bookImage = new ArrayList<>();

    private int pages;
    private Double price;
    private String paperType;
    private int stocks;
    private String isbn;
    private String dateOfPublished;
    private String dateOfUpload;

    public void addImage(BookImage bookImage){
        this.bookImage.add(bookImage);
    }

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

    public static Book saveFromDto(BooksRequestDto booksDto){
        return new Book(booksDto.getTitle(), booksDto.getAuthorName(),
                booksDto.getSynopsis(), booksDto.getTags(),
                booksDto.getCategories(), booksDto.getPages(), booksDto.getPrice(),
                booksDto.getPaperType(), booksDto.getStocks(), booksDto.getIsbn(),
                booksDto.getDateOfPublished(), booksDto.getDateOfUpload());
    }


}
