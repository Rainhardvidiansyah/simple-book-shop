package com.auth.jwt.dto.utils.repository;

import com.auth.jwt.model.BookImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookImageRepo extends JpaRepository<BookImage, Long> {
}
