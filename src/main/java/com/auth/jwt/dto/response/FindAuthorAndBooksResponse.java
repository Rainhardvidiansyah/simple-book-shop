package com.auth.jwt.dto.response;


import com.auth.jwt.model.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FindAuthorAndBooksResponse {

    private String bookTitle;
    private String author;
    private String synopsis;
    private int pages;
    private String price;
    private String paperType;
    private int stocks;
    private String isbn;
    private String dateOfPublished;

    public FindAuthorAndBooksResponse(String bookTitle, String author, String synopsis, int pages,
                                      String price, String paperType, int stocks, String isbn,
                                      String dateOfPublished) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.synopsis = synopsis;
        this.pages = pages;
        this.price = price;
        this.paperType = paperType;
        this.stocks = stocks;
        this.isbn = isbn;
        this.dateOfPublished = dateOfPublished;
    }

    public static FindAuthorAndBooksResponse From(Book book){
//        new FindAuthorAndBooksResponse(
//                books.getTitle(), books.getAuthor(), books.getSynopsis(), books.getPages(),
//                books.getPrice(), books.getPaperType(), books.getStocks(),
//                books.getIsbn(), books.getDateOfPublished());
        FindAuthorAndBooksResponse response = new FindAuthorAndBooksResponse();
        response.setBookTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        return response;
    }
}
