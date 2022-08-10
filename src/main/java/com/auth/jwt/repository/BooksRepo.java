package com.auth.jwt.repository;

import com.auth.jwt.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepo extends JpaRepository<Books, Long> {

    Optional <Books> findBooksByTitle(String title);
}
