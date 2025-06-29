package com.library.repository;

import com.library.entity.Book;
import com.library.entity.BookStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Kitap Repository Interface
 * 
 * Bu interface kitap entity'si için veritabanı işlemlerini tanımlar.
 * Java 8 Streams ve Lambda kullanımını gösterir.
 */
@Repository
public interface BookRepository extends GenericRepository<Book, Long> {

    /**
     * ISBN'e göre kitap arar
     * @param isbn ISBN numarası
     * @return Kitap (varsa)
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Başlığa göre kitapları arar (case-insensitive)
     * @param title Kitap başlığı
     * @return Kitap listesi
     */
    List<Book> findByTitleContainingIgnoreCase(String title);

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
     * Yayın yılına göre kitapları arar
     * @param publicationYear Yayın yılı
     * @return Kitap listesi
     */
    List<Book> findByPublicationYear(Integer publicationYear);

    /**
     * Dil'e göre kitapları arar
     * @param language Dil
     * @return Kitap listesi
     */
    List<Book> findByLanguage(String language);

    /**
     * Yayınevine göre kitapları arar
     * @param publisher Yayınevi
     * @return Kitap listesi
     */
    List<Book> findByPublisher(String publisher);

    /**
     * Mevcut kitapları arar (AVAILABLE durumunda olanlar)
     * @return Mevcut kitap listesi
     */
    @Query("SELECT b FROM Book b WHERE b.status = 'AVAILABLE'")
    List<Book> findAvailableBooks();

    /**
     * Ödünç verilmiş kitapları arar
     * @return Ödünç verilmiş kitap listesi
     */
    @Query("SELECT b FROM Book b WHERE b.status = 'BORROWED'")
    List<Book> findBorrowedBooks();

    /**
     * Belirli bir yıl aralığındaki kitapları arar
     * @param startYear Başlangıç yılı
     * @param endYear Bitiş yılı
     * @return Kitap listesi
     */
    @Query("SELECT b FROM Book b WHERE b.publicationYear BETWEEN :startYear AND :endYear")
    List<Book> findByPublicationYearBetween(@Param("startYear") Integer startYear, 
                                           @Param("endYear") Integer endYear);

    /**
     * Belirli bir fiyat aralığındaki kitapları arar
     * @param minPrice Minimum fiyat
     * @param maxPrice Maksimum fiyat
     * @return Kitap listesi
     */
    @Query("SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice")
    List<Book> findByPriceBetween(@Param("minPrice") Double minPrice, 
                                 @Param("maxPrice") Double maxPrice);

    /**
     * Kategori ID'sine göre kitapları arar
     * @param categoryId Kategori ID'si
     * @return Kitap listesi
     */
    @Query("SELECT DISTINCT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    List<Book> findByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * En popüler kitapları arar (en çok ödünç alınanlar)
     * @param limit Limit sayısı
     * @return Kitap listesi
     */
    @Query("SELECT b, COUNT(br) as borrowCount FROM Book b " +
           "LEFT JOIN b.borrowRecords br " +
           "GROUP BY b " +
           "ORDER BY borrowCount DESC")
    List<Object[]> findMostPopularBooks(@Param("limit") int limit);

    /**
     * Eski kitapları arar (10 yıldan eski)
     * @return Eski kitap listesi
     */
    @Query("SELECT b FROM Book b WHERE b.publicationYear < :currentYear - 10")
    List<Book> findOldBooks(@Param("currentYear") int currentYear);

    /**
     * Belirli bir konumdaki kitapları arar
     * @param location Konum
     * @return Kitap listesi
     */
    List<Book> findByLocation(String location);

    /**
     * Sayfa sayısına göre kitapları arar
     * @param minPages Minimum sayfa sayısı
     * @param maxPages Maksimum sayfa sayısı
     * @return Kitap listesi
     */
    @Query("SELECT b FROM Book b WHERE b.pageCount BETWEEN :minPages AND :maxPages")
    List<Book> findByPageCountBetween(@Param("minPages") Integer minPages, 
                                     @Param("maxPages") Integer maxPages);

    /**
     * Mevcut kitap sayısını döndürür
     * @return Mevcut kitap sayısı
     */
    @Query("SELECT COUNT(b) FROM Book b WHERE b.status = 'AVAILABLE'")
    long countAvailableBooks();

    /**
     * Toplam kitap sayısını döndürür
     * @return Toplam kitap sayısı
     */
    @Query("SELECT COUNT(b) FROM Book b")
    long countTotalBooks();
} 