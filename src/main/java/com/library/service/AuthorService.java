package com.library.service;

import com.library.entity.Author;
import java.util.List;
import java.util.Optional;

/**
 * Yazar servis interface'i
 * 
 * Bu interface yazar işlemleri için servis metodlarını tanımlar.
 */
public interface AuthorService extends GenericService<Author, Long> {

    /**
     * İsme göre yazar arar
     * @param name Yazar adı
     * @return Yazar listesi
     */
    List<Author> findByName(String name);

    /**
     * Email'e göre yazar arar
     * @param email Email adresi
     * @return Yazar
     */
    Optional<Author> findByEmail(String email);

    /**
     * Milliyete göre yazarları getirir
     * @param nationality Milliyet
     * @return Yazar listesi
     */
    List<Author> findByNationality(String nationality);

    /**
     * Doğum yılı aralığına göre yazarları getirir
     * @param startYear Başlangıç yılı
     * @param endYear Bitiş yılı
     * @return Yazar listesi
     */
    List<Author> findByBirthYearBetween(Integer startYear, Integer endYear);

    /**
     * En çok kitabı olan yazarları getirir
     * @param limit Limit sayısı
     * @return Yazar listesi
     */
    List<Author> findMostProlificAuthors(int limit);
} 