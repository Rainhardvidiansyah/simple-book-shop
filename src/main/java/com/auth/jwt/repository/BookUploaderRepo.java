package com.auth.jwt.repository;

import com.auth.jwt.activities.BookUploader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookUploaderRepo extends JpaRepository<BookUploader, Long> {

}
