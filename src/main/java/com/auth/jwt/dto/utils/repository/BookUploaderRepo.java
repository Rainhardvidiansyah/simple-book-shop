package com.auth.jwt.dto.utils.repository;

import com.auth.jwt.activities.BookUploader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookUploaderRepo extends JpaRepository<BookUploader, Long> {

}
