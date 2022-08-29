package com.auth.jwt.dto.utils.repository;

import com.auth.jwt.model.Category;
import com.auth.jwt.model.ECategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Optional<Category> findCategoryByCategoryName(ECategory category);
}
