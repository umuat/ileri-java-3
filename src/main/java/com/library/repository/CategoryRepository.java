package com.library.repository;

import com.library.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Kategori Repository Interface
 */
@Repository
public interface CategoryRepository extends GenericRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE SIZE(c.books) > 0 ORDER BY SIZE(c.books) DESC")
    List<Category> findMostPopularCategories(@Param("limit") int limit);

    @Query("SELECT c FROM Category c WHERE SIZE(c.books) = 0")
    List<Category> findEmptyCategories();

    List<Category> findByColorCode(String colorCode);
} 