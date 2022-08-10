package com.auth.jwt.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
DTO CLASS FOR BOOKS CONTROLLER
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BooksDto {

    private String title;
    private String synopsis;
    private int pages;
    private String price;
    private String paperType;
    private int stocks;
    private String isbn;
    private String dateOfPublished;
    private String dateOfUpload;
}
