package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.BookStatus;
import com.library.entity.Category;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Kitap Service Implementation
 * 
 * Bu sınıf kitap işlemleri için service metodlarını implement eder.
 * Java 8 Streams, Lambda ve Collections Framework kullanımını gösterir.
 */
@Service
@Transactional
public class BookServiceImpl extends GenericServiceImpl<Book, Long> implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        super(bookRepository);
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findByIsbn(String isbn) {
        log.debug("ISBN {} ile kitap aranıyor", isbn);
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByTitle(String title) {
        log.debug("Başlık '{}' ile kitaplar aranıyor", title);
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAuthorId(Long authorId) {
        log.debug("Yazar ID {} ile kitaplar aranıyor", authorId);
        return bookRepository.findByAuthorId(authorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByStatus(BookStatus status) {
        log.debug("Durum {} ile kitaplar aranıyor", status);
        return bookRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAvailableBooks() {
        log.debug("Mevcut kitaplar getiriliyor");
        return bookRepository.findAvailableBooks();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findBorrowedBooks() {
        log.debug("Ödünç verilmiş kitaplar getiriliyor");
        return bookRepository.findBorrowedBooks();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByCategoryId(Long categoryId) {
        log.debug("Kategori ID {} ile kitaplar aranıyor", categoryId);
        return bookRepository.findByCategoryId(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByPublicationYearBetween(Integer startYear, Integer endYear) {
        log.debug("Yayın yılı {} - {} arasındaki kitaplar aranıyor", startYear, endYear);
        return bookRepository.findByPublicationYearBetween(startYear, endYear);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByPriceBetween(Double minPrice, Double maxPrice) {
        log.debug("Fiyat {} - {} arasındaki kitaplar aranıyor", minPrice, maxPrice);
        return bookRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findMostPopularBooks(int limit) {
        log.debug("En popüler {} kitap getiriliyor", limit);
        List<Object[]> results = bookRepository.findMostPopularBooks(limit);
        
        // Stream API kullanarak sonuçları dönüştür
        return results.stream()
                .map(result -> (Book) result[0])
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findOldBooks() {
        log.debug("Eski kitaplar getiriliyor");
        int currentYear = LocalDate.now().getYear();
        return bookRepository.findOldBooks(currentYear);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<BookStatus, Long> getBookStatusStatistics() {
        log.debug("Kitap durum istatistikleri hesaplanıyor");
        
        // Stream API kullanarak istatistikleri hesapla
        return Arrays.stream(BookStatus.values())
                .collect(Collectors.toMap(
                    status -> status,
                    status -> (long) bookRepository.findByStatus(status).size()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getCategoryStatistics() {
        log.debug("Kategori istatistikleri hesaplanıyor");
        
        List<Book> allBooks = bookRepository.findAll();
        
        // Stream API kullanarak kategori bazında gruplama
        return allBooks.stream()
                .flatMap(book -> book.getCategories().stream())
                .collect(Collectors.groupingBy(
                    Category::getName,
                    Collectors.counting()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Integer, Long> getPublicationYearStatistics() {
        log.debug("Yayın yılı istatistikleri hesaplanıyor");
        
        List<Book> allBooks = bookRepository.findAll();
        
        // Stream API kullanarak yayın yılı bazında gruplama
        return allBooks.stream()
                .filter(book -> book.getPublicationYear() != null)
                .collect(Collectors.groupingBy(
                    Book::getPublicationYear,
                    Collectors.counting()
                ));
    }

    @Override
    public Book makeAvailable(Long bookId) {
        log.debug("Kitap {} mevcut hale getiriliyor", bookId);
        return updateBookStatus(bookId, BookStatus.AVAILABLE);
    }

    @Override
    public Book makeBorrowed(Long bookId) {
        log.debug("Kitap {} ödünç verilmiş olarak işaretleniyor", bookId);
        return updateBookStatus(bookId, BookStatus.BORROWED);
    }

    @Override
    public Book makeReserved(Long bookId) {
        log.debug("Kitap {} rezerve edilmiş olarak işaretleniyor", bookId);
        return updateBookStatus(bookId, BookStatus.RESERVED);
    }

    @Override
    public Book markAsLost(Long bookId) {
        log.debug("Kitap {} kayıp olarak işaretleniyor", bookId);
        return updateBookStatus(bookId, BookStatus.LOST);
    }

    @Override
    public Book markAsDamaged(Long bookId) {
        log.debug("Kitap {} hasarlı olarak işaretleniyor", bookId);
        return updateBookStatus(bookId, BookStatus.DAMAGED);
    }

    @Override
    public Book markAsUnderMaintenance(Long bookId) {
        log.debug("Kitap {} bakımda olarak işaretleniyor", bookId);
        return updateBookStatus(bookId, BookStatus.UNDER_MAINTENANCE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> searchBooks(String title, String authorName, String categoryName, BookStatus status) {
        log.debug("Kitap arama yapılıyor: title={}, author={}, category={}, status={}", 
                 title, authorName, categoryName, status);
        
        List<Book> allBooks = bookRepository.findAll();
        
        return allBooks.stream()
                .filter(book -> title == null || title.isEmpty() || 
                               book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> authorName == null || authorName.isEmpty() || 
                               (book.getAuthor() != null && 
                                book.getAuthor().getName().toLowerCase().contains(authorName.toLowerCase())))
                .filter(book -> categoryName == null || categoryName.isEmpty() || 
                               book.getCategories().stream()
                                   .anyMatch(cat -> cat.getName().toLowerCase().contains(categoryName.toLowerCase())))
                .filter(book -> status == null || book.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBookRecommendations(Long bookId, int limit) {
        log.debug("Kitap {} için öneriler getiriliyor", bookId);
        
        Optional<Book> targetBook = findById(bookId);
        if (!targetBook.isPresent()) {
            return new ArrayList<>();
        }
        
        Book book = targetBook.get();
        List<Book> allBooks = bookRepository.findAll();
        
        // Benzer kategorilerdeki kitapları öner
        return allBooks.stream()
                .filter(b -> !b.getId().equals(bookId))
                .filter(b -> b.getAuthor() != null && book.getAuthor() != null && 
                           b.getAuthor().getId().equals(book.getAuthor().getId()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private Book updateBookStatus(Long bookId, BookStatus status) {
        Optional<Book> bookOpt = findById(bookId);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setStatus(status);
            return update(book);
        }
        throw new RuntimeException("Kitap bulunamadı: " + bookId);
    }
} 