package com.auth.jwt.dto;


import com.auth.jwt.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/*
DTO CLASS FOR BOOKS CONTROLLER
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BooksDto {

    private String title;
    private String authorName;
    private String synopsis;
    private String tags;
    private Set<Category> categories = new HashSet<>();
    private int pages;
    private Double price;
    private String paperType;
    private int stocks;
    private String isbn;
    private String dateOfPublished;
    private String dateOfUpload;

}
