package com.auth.jwt.model;

import com.auth.jwt.dto.request.BookImageRequest;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class BookImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        private String name;
		private String type;
		@Lob
		private byte[] filecontent;
     */
    private String imageName;
    @Lob
    private byte[] imageData;
    private String description;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public BookImage(byte[] imageData, String imageName, String description) {
        this.imageData = imageData;
        this.imageName = imageName;
        this.description = description;
    }
}
