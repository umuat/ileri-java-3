package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Generic Repository Interface
 * 
 * Bu interface tüm entity'ler için ortak repository işlemlerini tanımlar.
 * Generic programlama kullanarak tip güvenliği sağlar.
 * 
 * @param <T> Entity tipi
 * @param <ID> ID tipi
 */
@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    
    /**
     * Aktif kayıtları sayar
     * @return Aktif kayıt sayısı
     */
    default long countActive() {
        return count();
    }
    
    /**
     * Belirtilen ID'ye sahip kaydın var olup olmadığını kontrol eder
     * @param id Kayıt ID'si
     * @return Varsa true
     */
    default boolean existsById(ID id) {
        return findById(id).isPresent();
    }
} 