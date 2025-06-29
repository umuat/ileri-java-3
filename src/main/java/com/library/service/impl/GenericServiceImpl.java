package com.library.service.impl;

import com.library.repository.GenericRepository;
import com.library.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Generic Service Implementation
 * 
 * Bu sınıf tüm entity'ler için ortak service işlemlerini implement eder.
 * Multithreading ve asenkron işlemleri gösterir.
 * 
 * @param <T> Entity tipi
 * @param <ID> ID tipi
 */
@Transactional
public abstract class GenericServiceImpl<T, ID> implements GenericService<T, ID> {

    private static final Logger log = LoggerFactory.getLogger(GenericServiceImpl.class);

    protected final GenericRepository<T, ID> repository;

    protected GenericServiceImpl(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        log.debug("Tüm kayıtlar getiriliyor");
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        log.debug("ID {} ile kayıt aranıyor", id);
        return repository.findById(id);
    }

    @Override
    public T save(T entity) {
        log.debug("Yeni kayıt oluşturuluyor: {}", entity);
        T savedEntity = repository.save(entity);
        log.info("Kayıt başarıyla oluşturuldu: {}", savedEntity);
        return savedEntity;
    }

    @Override
    public T update(T entity) {
        log.debug("Kayıt güncelleniyor: {}", entity);
        T updatedEntity = repository.save(entity);
        log.info("Kayıt başarıyla güncellendi: {}", updatedEntity);
        return updatedEntity;
    }

    @Override
    public void deleteById(ID id) {
        log.debug("ID {} ile kayıt siliniyor", id);
        repository.deleteById(id);
        log.info("Kayıt başarıyla silindi: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        log.debug("ID {} ile kayıt varlığı kontrol ediliyor", id);
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Toplam kayıt sayısı hesaplanıyor");
        return repository.count();
    }

    /**
     * Asenkron kayıt işlemi
     * @param entity Kaydedilecek entity
     * @return CompletableFuture
     */
    @Async
    public CompletableFuture<T> saveAsync(T entity) {
        log.debug("Yeni kayıt asenkron olarak oluşturuluyor: {}", entity);
        return CompletableFuture.completedFuture(save(entity));
    }

    /**
     * Asenkron güncelleme işlemi
     * @param entity Güncellenecek entity
     * @return CompletableFuture
     */
    @Async
    public CompletableFuture<T> updateAsync(T entity) {
        log.debug("Kayıt asenkron olarak güncelleniyor: {}", entity);
        return CompletableFuture.completedFuture(update(entity));
    }

    /**
     * Asenkron silme işlemi
     * @param id Silinecek ID
     * @return CompletableFuture
     */
    @Async
    public CompletableFuture<Void> deleteByIdAsync(ID id) {
        log.debug("ID {} ile kayıt asenkron olarak siliniyor", id);
        deleteById(id);
        return CompletableFuture.completedFuture(null);
    }
} 