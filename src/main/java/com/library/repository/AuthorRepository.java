package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Yazar Repository Interface
 */
@Repository
public interface AuthorRepository extends GenericRepository<Author, Long> {

    Optional<Author> findByEmail(String email);
    
    Optional<Author> findByName(String name);
    
    List<Author> findByNameContainingIgnoreCase(String name);
    
    List<Author> findByNationality(String nationality);
    
    @Query("SELECT a FROM Author a WHERE a.birthYear BETWEEN :startYear AND :endYear")
    List<Author> findByBirthYearBetween(@Param("startYear") Integer startYear, 
                                       @Param("endYear") Integer endYear);
    
    @Query("SELECT a FROM Author a WHERE SIZE(a.books) > 0 ORDER BY SIZE(a.books) DESC")
    List<Author> findAuthorsWithBooks();

    @Query("SELECT a FROM Author a WHERE SIZE(a.books) > 0 ORDER BY SIZE(a.books) DESC")
    List<Author> findMostProlificAuthors(@Param("limit") int limit);
} 