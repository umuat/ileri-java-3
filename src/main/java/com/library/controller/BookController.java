package com.library.controller;

import com.library.entity.Book;
import com.library.entity.BookStatus;
import com.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Kitap REST API Controller
 * 
 * Bu controller kitap işlemleri için REST API endpoint'lerini sağlar.
 * Web API ve J2EE temellerini gösterir.
 */
@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Tüm kitapları getirir
     * @return Kitap listesi
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("Tüm kitaplar getiriliyor");
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    /**
     * ID'ye göre kitap getirir
     * @param id Kitap ID'si
     * @return Kitap
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        log.info("ID {} ile kitap getiriliyor", id);
        Optional<Book> book = bookService.findById(id);
        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * ISBN'e göre kitap getirir
     * @param isbn ISBN numarası
     * @return Kitap
     */
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        log.info("ISBN {} ile kitap getiriliyor", isbn);
        Optional<Book> book = bookService.findByIsbn(isbn);
        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Başlığa göre kitapları arar
     * @param title Kitap başlığı
     * @return Kitap listesi
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<Book>> searchByTitle(@RequestParam String title) {
        log.info("Başlık '{}' ile kitaplar aranıyor", title);
        List<Book> books = bookService.findByTitle(title);
        return ResponseEntity.ok(books);
    }

    /**
     * Yazar ID'sine göre kitapları getirir
     * @param authorId Yazar ID'si
     * @return Kitap listesi
     */
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable Long authorId) {
        log.info("Yazar ID {} ile kitaplar getiriliyor", authorId);
        List<Book> books = bookService.findByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }

    /**
     * Duruma göre kitapları getirir
     * @param status Kitap durumu
     * @return Kitap listesi
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Book>> getBooksByStatus(@PathVariable BookStatus status) {
        log.info("Durum {} ile kitaplar getiriliyor", status);
        List<Book> books = bookService.findByStatus(status);
        return ResponseEntity.ok(books);
    }

    /**
     * Mevcut kitapları getirir
     * @return Mevcut kitap listesi
     */
    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        log.info("Mevcut kitaplar getiriliyor");
        List<Book> books = bookService.findAvailableBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Ödünç verilmiş kitapları getirir
     * @return Ödünç verilmiş kitap listesi
     */
    @GetMapping("/borrowed")
    public ResponseEntity<List<Book>> getBorrowedBooks() {
        log.info("Ödünç verilmiş kitaplar getiriliyor");
        List<Book> books = bookService.findBorrowedBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Kategori ID'sine göre kitapları getirir
     * @param categoryId Kategori ID'si
     * @return Kitap listesi
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable Long categoryId) {
        log.info("Kategori ID {} ile kitaplar getiriliyor", categoryId);
        List<Book> books = bookService.findByCategoryId(categoryId);
        return ResponseEntity.ok(books);
    }

    /**
     * Yayın yılı aralığına göre kitapları getirir
     * @param startYear Başlangıç yılı
     * @param endYear Bitiş yılı
     * @return Kitap listesi
     */
    @GetMapping("/year-range")
    public ResponseEntity<List<Book>> getBooksByYearRange(
            @RequestParam Integer startYear, 
            @RequestParam Integer endYear) {
        log.info("Yayın yılı {} - {} arasındaki kitaplar getiriliyor", startYear, endYear);
        List<Book> books = bookService.findByPublicationYearBetween(startYear, endYear);
        return ResponseEntity.ok(books);
    }

    /**
     * Fiyat aralığına göre kitapları getirir
     * @param minPrice Minimum fiyat
     * @param maxPrice Maksimum fiyat
     * @return Kitap listesi
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<Book>> getBooksByPriceRange(
            @RequestParam Double minPrice, 
            @RequestParam Double maxPrice) {
        log.info("Fiyat {} - {} arasındaki kitaplar getiriliyor", minPrice, maxPrice);
        List<Book> books = bookService.findByPriceBetween(minPrice, maxPrice);
        return ResponseEntity.ok(books);
    }

    /**
     * En popüler kitapları getirir
     * @param limit Limit sayısı
     * @return Kitap listesi
     */
    @GetMapping("/popular")
    public ResponseEntity<List<Book>> getMostPopularBooks(@RequestParam(defaultValue = "10") int limit) {
        log.info("En popüler {} kitap getiriliyor", limit);
        List<Book> books = bookService.findMostPopularBooks(limit);
        return ResponseEntity.ok(books);
    }

    /**
     * Eski kitapları getirir
     * @return Eski kitap listesi
     */
    @GetMapping("/old")
    public ResponseEntity<List<Book>> getOldBooks() {
        log.info("Eski kitaplar getiriliyor");
        List<Book> books = bookService.findOldBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Kitap durum istatistiklerini getirir
     * @return Durum istatistikleri
     */
    @GetMapping("/statistics/status")
    public ResponseEntity<Map<BookStatus, Long>> getBookStatusStatistics() {
        log.info("Kitap durum istatistikleri getiriliyor");
        Map<BookStatus, Long> statistics = bookService.getBookStatusStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * Kategori istatistiklerini getirir
     * @return Kategori istatistikleri
     */
    @GetMapping("/statistics/categories")
    public ResponseEntity<Map<String, Long>> getCategoryStatistics() {
        log.info("Kategori istatistikleri getiriliyor");
        Map<String, Long> statistics = bookService.getCategoryStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * Yayın yılı istatistiklerini getirir
     * @return Yayın yılı istatistikleri
     */
    @GetMapping("/statistics/years")
    public ResponseEntity<Map<Integer, Long>> getPublicationYearStatistics() {
        log.info("Yayın yılı istatistikleri getiriliyor");
        Map<Integer, Long> statistics = bookService.getPublicationYearStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * Gelişmiş kitap arama
     * @param title Kitap başlığı
     * @param authorName Yazar adı
     * @param categoryName Kategori adı
     * @param status Kitap durumu
     * @return Kitap listesi
     */
    @GetMapping("/search/advanced")
    public ResponseEntity<List<Book>> advancedSearch(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) BookStatus status) {
        log.info("Gelişmiş arama yapılıyor: title={}, author={}, category={}, status={}", 
                title, authorName, categoryName, status);
        List<Book> books = bookService.searchBooks(title, authorName, categoryName, status);
        return ResponseEntity.ok(books);
    }

    /**
     * Kitap önerilerini getirir
     * @param bookId Referans kitap ID'si
     * @param limit Öneri sayısı
     * @return Kitap listesi
     */
    @GetMapping("/{bookId}/recommendations")
    public ResponseEntity<List<Book>> getBookRecommendations(
            @PathVariable Long bookId, 
            @RequestParam(defaultValue = "5") int limit) {
        log.info("Kitap {} için öneriler getiriliyor", bookId);
        List<Book> recommendations = bookService.getBookRecommendations(bookId, limit);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Yeni kitap oluşturur
     * @param book Kitap bilgileri
     * @return Oluşturulan kitap
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        log.info("Yeni kitap oluşturuluyor: {}", book.getTitle());
        Book createdBook = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    /**
     * Kitap günceller
     * @param id Kitap ID'si
     * @param book Güncellenecek kitap bilgileri
     * @return Güncellenmiş kitap
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        log.info("Kitap {} güncelleniyor", id);
        book.setId(id);
        Book updatedBook = bookService.update(book);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Kitap siler
     * @param id Kitap ID'si
     * @return Boş response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.info("Kitap {} siliniyor", id);
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Kitabı mevcut hale getirir
     * @param id Kitap ID'si
     * @return Güncellenmiş kitap
     */
    @PatchMapping("/{id}/make-available")
    public ResponseEntity<Book> makeBookAvailable(@PathVariable Long id) {
        log.info("Kitap {} mevcut hale getiriliyor", id);
        Book book = bookService.makeAvailable(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Kitabı ödünç verilmiş olarak işaretler
     * @param id Kitap ID'si
     * @return Güncellenmiş kitap
     */
    @PatchMapping("/{id}/make-borrowed")
    public ResponseEntity<Book> makeBookBorrowed(@PathVariable Long id) {
        log.info("Kitap {} ödünç verilmiş olarak işaretleniyor", id);
        Book book = bookService.makeBorrowed(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Kitabı rezerve edilmiş olarak işaretler
     * @param id Kitap ID'si
     * @return Güncellenmiş kitap
     */
    @PatchMapping("/{id}/make-reserved")
    public ResponseEntity<Book> makeBookReserved(@PathVariable Long id) {
        log.info("Kitap {} rezerve edilmiş olarak işaretleniyor", id);
        Book book = bookService.makeReserved(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Kitabı kayıp olarak işaretler
     * @param id Kitap ID'si
     * @return Güncellenmiş kitap
     */
    @PatchMapping("/{id}/mark-lost")
    public ResponseEntity<Book> markBookAsLost(@PathVariable Long id) {
        log.info("Kitap {} kayıp olarak işaretleniyor", id);
        Book book = bookService.markAsLost(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Kitabı hasarlı olarak işaretler
     * @param id Kitap ID'si
     * @return Güncellenmiş kitap
     */
    @PatchMapping("/{id}/mark-damaged")
    public ResponseEntity<Book> markBookAsDamaged(@PathVariable Long id) {
        log.info("Kitap {} hasarlı olarak işaretleniyor", id);
        Book book = bookService.markAsDamaged(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Kitabı bakımda olarak işaretler
     * @param id Kitap ID'si
     * @return Güncellenmiş kitap
     */
    @PatchMapping("/{id}/mark-maintenance")
    public ResponseEntity<Book> markBookAsUnderMaintenance(@PathVariable Long id) {
        log.info("Kitap {} bakımda olarak işaretleniyor", id);
        Book book = bookService.markAsUnderMaintenance(id);
        return ResponseEntity.ok(book);
    }
} 