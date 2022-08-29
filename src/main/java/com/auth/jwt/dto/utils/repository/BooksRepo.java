package com.auth.jwt.dto.utils.repository;

import com.auth.jwt.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepo extends JpaRepository<Book, Long> {

    Optional <Book> findBooksByTitle(String title);
    List<Book> findByAuthorOrderByTitleAsc(String author);

    @Query("Select b from Book b where b.title like %:title%")
    List<Book> findTitleLike(@Param("title") String title);

    @Query("Select b from Book b where b.tags like %:tags%")
    List<Book> findTagsLike(@Param("tags") String tags);




}
