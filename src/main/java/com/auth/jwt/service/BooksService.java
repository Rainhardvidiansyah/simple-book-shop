package com.auth.jwt.service;


import com.auth.jwt.model.Book;
import com.auth.jwt.model.BookImage;

import com.auth.jwt.repository.BookImageRepo;
import com.auth.jwt.repository.BooksRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepo booksRepo;
    private final BookImageRepo imageRepo;

    private BigDecimal setPrice(String price){
        BigDecimal validAmount = new BigDecimal(price);
        return validAmount;
    }

    private String convertPrice(String price){
        BigDecimal bigDecimal = new BigDecimal(price);
        return bigDecimal.toString();
    }

    private static String setDate(){
        Date dateNow = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
        var setDateFormat = simpleDateFormat.format(dateNow);
        return setDateFormat;
    }

    public Book saveBooks(Book book){
        book.setDateOfUpload(setDate());
        return booksRepo.save(book);
    }

    public Optional<Book> findBookTitle(String title){
        return booksRepo.findBooksByTitle(title);
    }

    public List<Book> findBooksByTags(String tags){
        return booksRepo.findBookByTagsContainingIgnoreCase(tags);
    }

    public List<Book> findBooksByTitle(String title){
        return booksRepo.findBookByTitleContainingIgnoreCase(title);
    }

    @Transactional
    public List<Book> findAllBooks(){
        return booksRepo.findAll();
    }

    public List<Book> findBooksByAuthorName(String authorName){
        return booksRepo.findByAuthorContainingIgnoreCaseOrderByTitleAsc(authorName);
    }

    public Optional<Book> findBookId(Long id){
        return booksRepo.findById(id);
    }

    public Book updateBook(Long id, Book book){
        Book updateBook
                = booksRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Book with id %d not found", id)));
        updateBook.setTitle(book.getTitle());
        updateBook.setAuthor(book.getAuthor());
        updateBook.setSynopsis(book.getSynopsis());
        updateBook.setPages(book.getPages());
        updateBook.setPrice(book.getPrice());
        updateBook.setPaperType(book.getPaperType());
        updateBook.setStocks(book.getStocks());
        updateBook.setIsbn(book.getIsbn());
        updateBook.setDateOfPublished(book.getDateOfPublished());
        updateBook.setDateOfUpload(book.getDateOfUpload());
        updateBook.setDateOfUpload(setDate());
        return booksRepo.save(updateBook);
    }

    @Transactional
    public void deleteBook(Long id){
        booksRepo.deleteById(id);
    }

    public Book insertImage(Long bookId, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        var book = booksRepo.findById(bookId).orElseThrow(RuntimeException::new);
        var bookImage = new BookImage();
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException();
            }
            bookImage.setImageName(fileName);
            bookImage.setImageData(file.getBytes());
            bookImage.setBook(book);
            bookImage.setContentType(file.getContentType());
        }catch (IOException e) {
            e.printStackTrace();
        }
        book.addImage(bookImage);
        BookImage savedImage = imageRepo.save(bookImage);
        return book;
    }

    public void addAnotherImage(Long id, MultipartFile[] files) {
        Optional<Book> book = booksRepo.findById(id);
        if (book.isEmpty()) {
            throw new RuntimeException("Not found any book");
        }
        var bookImage = new BookImage();
        List<BookImage> imageList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                bookImage.setImageName(file.getOriginalFilename());
                bookImage.setImageData(file.getBytes());
                bookImage.setContentType(file.getContentType());
                bookImage.setBook(book.get());
                imageList.add(bookImage);
                imageRepo.saveAll(imageList);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return;
    }


}
