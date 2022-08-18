package com.auth.jwt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class BookImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String imageName;
    @Lob
    private byte[] imageData;
    private String contentType;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public BookImage(byte[] imageData, String imageName, String contentType) {
        this.imageData = imageData;
        this.imageName = imageName;
        this.contentType = contentType;
    }
}
