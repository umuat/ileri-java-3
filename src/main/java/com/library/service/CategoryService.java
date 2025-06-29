package com.library.service;

import com.library.entity.Category;
import java.util.List;
import java.util.Optional;

/**
 * Kategori servis interface'i
 * 
 * Bu interface kategori işlemleri için servis metodlarını tanımlar.
 */
public interface CategoryService extends GenericService<Category, Long> {

    /**
     * İsme göre kategori arar
     * @param name Kategori adı
     * @return Kategori
     */
    Optional<Category> findByName(String name);

    /**
     * En çok kitabı olan kategorileri getirir
     * @param limit Limit sayısı
     * @return Kategori listesi
     */
    List<Category> findMostPopularCategories(int limit);

    /**
     * Boş kategorileri getirir (hiç kitabı olmayan)
     * @return Kategori listesi
     */
    List<Category> findEmptyCategories();

    /**
     * Renk koduna göre kategorileri getirir
     * @param colorCode Renk kodu
     * @return Kategori listesi
     */
    List<Category> findByColorCode(String colorCode);
} 