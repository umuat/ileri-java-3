package com.library.service;

import java.util.List;
import java.util.Optional;

/**
 * Generic Service Interface
 * 
 * Bu interface tüm entity'ler için ortak service işlemlerini tanımlar.
 * Generic programlama kullanarak tip güvenliği sağlar.
 * 
 * @param <T> Entity tipi
 * @param <ID> ID tipi
 */
public interface GenericService<T, ID> {
    
    /**
     * Tüm kayıtları getirir
     * @return Kayıt listesi
     */
    List<T> findAll();
    
    /**
     * ID'ye göre kayıt arar
     * @param id Kayıt ID'si
     * @return Kayıt (varsa)
     */
    Optional<T> findById(ID id);
    
    /**
     * Yeni kayıt oluşturur
     * @param entity Oluşturulacak entity
     * @return Oluşturulan entity
     */
    T save(T entity);
    
    /**
     * Kaydı günceller
     * @param entity Güncellenecek entity
     * @return Güncellenmiş entity
     */
    T update(T entity);
    
    /**
     * ID'ye göre kaydı siler
     * @param id Silinecek kayıt ID'si
     */
    void deleteById(ID id);
    
    /**
     * Kaydın var olup olmadığını kontrol eder
     * @param id Kontrol edilecek ID
     * @return Varsa true
     */
    boolean existsById(ID id);
    
    /**
     * Toplam kayıt sayısını döndürür
     * @return Toplam kayıt sayısı
     */
    long count();
} 