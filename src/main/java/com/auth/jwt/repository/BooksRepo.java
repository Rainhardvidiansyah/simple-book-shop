package com.auth.jwt.repository;

import com.auth.jwt.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepo extends JpaRepository<Book, Long> {

    Optional <Book> findBooksByTitle(String title);
    List<Book> findByAuthorContainingIgnoreCaseOrderByTitleAsc(String author);
    List<Book> findBookByTitleContainingIgnoreCase(String title);
    List<Book> findBookByTagsContainingIgnoreCase(String tags);




}
