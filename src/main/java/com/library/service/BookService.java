package com.library.service;

import com.library.entity.Book;
import com.library.entity.BookStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Kitap Service Interface
 * 
 * Bu interface kitap işlemleri için service metodlarını tanımlar.
 * Java 8 Streams ve Lambda kullanımını gösterir.
 */
public interface BookService extends GenericService<Book, Long> {

    /**
     * ISBN'e göre kitap arar
     * @param isbn ISBN numarası
     * @return Kitap (varsa)
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Başlığa göre kitapları arar
     * @param title Kitap başlığı
     * @return Kitap listesi
     */
    List<Book> findByTitle(String title);

    /**
     * Yazar ID'sine göre kitapları arar
     * @param authorId Yazar ID'si
     * @return Kitap listesi
     */
    List<Book> findByAuthorId(Long authorId);

    /**
     * Duruma göre kitapları arar
     * @param status Kitap durumu
     * @return Kitap listesi
     */
    List<Book> findByStatus(BookStatus status);

    /**
     * Mevcut kitapları getirir
     * @return Mevcut kitap listesi
     */
    List<Book> findAvailableBooks();

    /**
     * Ödünç verilmiş kitapları getirir
     * @return Ödünç verilmiş kitap listesi
     */
    List<Book> findBorrowedBooks();

    /**
     * Kategori ID'sine göre kitapları arar
     * @param categoryId Kategori ID'si
     * @return Kitap listesi
     */
    List<Book> findByCategoryId(Long categoryId);

    /**
     * Belirli bir yıl aralığındaki kitapları arar
     * @param startYear Başlangıç yılı
     * @param endYear Bitiş yılı
     * @return Kitap listesi
     */
    List<Book> findByPublicationYearBetween(Integer startYear, Integer endYear);

    /**
     * Belirli bir fiyat aralığındaki kitapları arar
     * @param minPrice Minimum fiyat
     * @param maxPrice Maksimum fiyat
     * @return Kitap listesi
     */
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * En popüler kitapları getirir
     * @param limit Limit sayısı
     * @return Kitap listesi
     */
    List<Book> findMostPopularBooks(int limit);

    /**
     * Eski kitapları getirir (10 yıldan eski)
     * @return Eski kitap listesi
     */
    List<Book> findOldBooks();

    /**
     * Kitap durumlarının istatistiklerini getirir
     * @return Durum istatistikleri
     */
    Map<BookStatus, Long> getBookStatusStatistics();

    /**
     * Kategori bazında kitap sayılarını getirir
     * @return Kategori istatistikleri
     */
    Map<String, Long> getCategoryStatistics();

    /**
     * Yayın yılı bazında kitap sayılarını getirir
     * @return Yayın yılı istatistikleri
     */
    Map<Integer, Long> getPublicationYearStatistics();

    /**
     * Kitabı ödünç alınabilir hale getirir
     * @param bookId Kitap ID'si
     * @return Güncellenmiş kitap
     */
    Book makeAvailable(Long bookId);

    /**
     * Kitabı ödünç verilmiş olarak işaretler
     * @param bookId Kitap ID'si
     * @return Güncellenmiş kitap
     */
    Book makeBorrowed(Long bookId);

    /**
     * Kitabı rezerve edilmiş olarak işaretler
     * @param bookId Kitap ID'si
     * @return Güncellenmiş kitap
     */
    Book makeReserved(Long bookId);

    /**
     * Kitabı kayıp olarak işaretler
     * @param bookId Kitap ID'si
     * @return Güncellenmiş kitap
     */
    Book markAsLost(Long bookId);

    /**
     * Kitabı hasarlı olarak işaretler
     * @param bookId Kitap ID'si
     * @return Güncellenmiş kitap
     */
    Book markAsDamaged(Long bookId);

    /**
     * Kitabı bakımda olarak işaretler
     * @param bookId Kitap ID'si
     * @return Güncellenmiş kitap
     */
    Book markAsUnderMaintenance(Long bookId);

    /**
     * Kitap arama işlemi (gelişmiş)
     * @param title Başlık (opsiyonel)
     * @param authorName Yazar adı (opsiyonel)
     * @param categoryName Kategori adı (opsiyonel)
     * @param status Durum (opsiyonel)
     * @return Kitap listesi
     */
    List<Book> searchBooks(String title, String authorName, String categoryName, BookStatus status);

    /**
     * Kitap önerileri getirir
     * @param bookId Referans kitap ID'si
     * @param limit Limit sayısı
     * @return Önerilen kitap listesi
     */
    List<Book> getBookRecommendations(Long bookId, int limit);
} 