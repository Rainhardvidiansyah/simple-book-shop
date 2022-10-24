package com.auth.jwt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

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
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "image_data")
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
