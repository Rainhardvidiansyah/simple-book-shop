package com.auth.jwt.repository;

import com.auth.jwt.model.BookImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookImageRepo extends JpaRepository<BookImage, Long> {
}
