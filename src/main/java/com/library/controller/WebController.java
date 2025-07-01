package com.library.controller;

import com.library.entity.Book;
import com.library.entity.BookStatus;
import com.library.entity.Author;
import com.library.entity.Category;
import com.library.service.BookService;
import com.library.service.AuthorService;
import com.library.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Web Controller
 * 
 * Bu controller web sayfaları için endpoint'leri sağlar.
 * Thymeleaf template'leri için kullanılır.
 */
@Controller
public class WebController {

    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public WebController(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    /**
     * Ana sayfa
     * @param model Model
     * @return Ana sayfa template'i
     */
    @GetMapping("/")
    public String home(Model model) {
        log.info("Ana sayfa yükleniyor");
        
        // İstatistikleri hesapla
        long totalBooks = bookService.count();
        long availableBooks = bookService.findAvailableBooks().size();
        long borrowedBooks = bookService.findBorrowedBooks().size();
        
        // En popüler kitapları getir
        List<Book> popularBooks = bookService.findMostPopularBooks(5);
        
        // Model'e verileri ekle
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("availableBooks", availableBooks);
        model.addAttribute("borrowedBooks", borrowedBooks);
        model.addAttribute("popularBooks", popularBooks);
        
        return "index";
    }

    /**
     * Kitaplar sayfası
     * @param model Model
     * @return Kitaplar sayfası template'i
     */
    @GetMapping("/books")
    public String books(Model model) {
        log.info("Kitaplar sayfası yükleniyor");
        
        List<Book> allBooks = bookService.findAll();
        log.info("Toplam {} kitap bulundu", allBooks.size());
        
        // Her kitabın başlığını logla
        for (Book book : allBooks) {
            log.info("Kitap: {} (ID: {}, Durum: {})", book.getTitle(), book.getId(), book.getStatus());
        }
        
        model.addAttribute("books", allBooks);
        
        return "books";
    }

    /**
     * Mevcut kitaplar sayfası
     * @param model Model
     * @return Mevcut kitaplar sayfası template'i
     */
    @GetMapping("/books/available")
    public String availableBooks(Model model) {
        log.info("Mevcut kitaplar sayfası yükleniyor");
        
        List<Book> availableBooks = bookService.findAvailableBooks();
        model.addAttribute("books", availableBooks);
        model.addAttribute("title", "Mevcut Kitaplar");
        
        return "books";
    }

    /**
     * Ödünç verilmiş kitaplar sayfası
     * @param model Model
     * @return Ödünç verilmiş kitaplar sayfası template'i
     */
    @GetMapping("/books/borrowed")
    public String borrowedBooks(Model model) {
        log.info("Ödünç verilmiş kitaplar sayfası yükleniyor");
        
        List<Book> borrowedBooks = bookService.findBorrowedBooks();
        model.addAttribute("books", borrowedBooks);
        model.addAttribute("title", "Ödünç Verilmiş Kitaplar");
        
        return "books";
    }

    /**
     * Kitap arama sayfası
     * @param model Model
     * @param title Başlık
     * @param authorName Yazar adı
     * @param categoryName Kategori adı
     * @param status Durum
     * @return Arama sonuçları sayfası template'i
     */
    @GetMapping("/books/search")
    public String searchBooks(Model model,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) String authorName,
                             @RequestParam(required = false) String categoryName,
                             @RequestParam(required = false) BookStatus status) {
        log.info("Kitap arama sayfası yükleniyor");
        
        List<Book> searchResults = bookService.searchBooks(title, authorName, categoryName, status);
        model.addAttribute("books", searchResults);
        model.addAttribute("title", "Arama Sonuçları");
        model.addAttribute("searchTitle", title);
        model.addAttribute("searchAuthor", authorName);
        model.addAttribute("searchCategory", categoryName);
        model.addAttribute("searchStatus", status);
        
        return "books";
    }

    /**
     * En popüler kitaplar sayfası
     * @param model Model
     * @return Popüler kitaplar sayfası template'i
     */
    @GetMapping("/books/popular")
    public String popularBooks(Model model) {
        log.info("Popüler kitaplar sayfası yükleniyor");
        
        List<Book> popularBooks = bookService.findMostPopularBooks(10);
        model.addAttribute("books", popularBooks);
        model.addAttribute("title", "En Popüler Kitaplar");
        
        return "books";
    }

    /**
     * Eski kitaplar sayfası
     * @param model Model
     * @return Eski kitaplar sayfası template'i
     */
    @GetMapping("/books/old")
    public String oldBooks(Model model) {
        log.info("Eski kitaplar sayfası yükleniyor");
        
        List<Book> oldBooks = bookService.findOldBooks();
        model.addAttribute("books", oldBooks);
        model.addAttribute("title", "Eski Kitaplar (10+ Yıl)");
        
        return "books";
    }

    /**
     * İstatistikler sayfası
     * @param model Model
     * @return İstatistikler sayfası template'i
     */
    @GetMapping("/statistics")
    public String statistics(Model model) {
        log.info("İstatistikler sayfası yükleniyor");
        
        // Kitap durum istatistikleri
        Map<BookStatus, Long> statusStats = bookService.getBookStatusStatistics();
        model.addAttribute("statusStats", statusStats);
        
        // Kategori istatistikleri
        Map<String, Long> categoryStats = bookService.getCategoryStatistics();
        model.addAttribute("categoryStats", categoryStats);
        
        // Yayın yılı istatistikleri
        Map<Integer, Long> yearStats = bookService.getPublicationYearStatistics();
        model.addAttribute("yearStats", yearStats);
        
        return "statistics";
    }

    /**
     * Hakkında sayfası
     * @param model Model
     * @return Hakkında sayfası template'i
     */
    @GetMapping("/about")
    public String about(Model model) {
        log.info("Hakkında sayfası yükleniyor");
        return "about";
    }

    /**
     * İletişim sayfası
     * @param model Model
     * @return İletişim sayfası template'i
     */
    @GetMapping("/contact")
    public String contact(Model model) {
        log.info("İletişim sayfası yükleniyor");
        return "contact";
    }

    /**
     * Kitap ekleme formu sayfası
     * @param model Model
     * @return Kitap ekleme formu template'i
     */
    @GetMapping("/books/add")
    public String addBookForm(Model model) {
        log.info("Kitap ekleme formu yükleniyor");
        
        List<Author> authors = authorService.findAll();
        List<Category> categories = categoryService.findAll();
        
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        model.addAttribute("bookStatuses", BookStatus.values());
        
        return "book-form";
    }

    /**
     * Kitap ekleme işlemi
     * @param book Kitap
     * @param redirectAttributes Redirect attributes
     * @return Redirect
     */
    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        log.info("Yeni kitap ekleniyor: {}", book.getTitle());
        
        try {
            Book savedBook = bookService.save(book);
            redirectAttributes.addFlashAttribute("success", "Kitap başarıyla eklendi: " + savedBook.getTitle());
            return "redirect:/books";
        } catch (Exception e) {
            log.error("Kitap eklenirken hata oluştu", e);
            redirectAttributes.addFlashAttribute("error", "Kitap eklenirken hata oluştu: " + e.getMessage());
            return "redirect:/books/add";
        }
    }

    /**
     * Kitap düzenleme formu sayfası
     * @param id Kitap ID'si
     * @param model Model
     * @return Kitap düzenleme formu template'i
     */
    @GetMapping("/books/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        log.info("Kitap düzenleme formu yükleniyor, ID: {}", id);
        
        Optional<Book> bookOpt = bookService.findById(id);
        if (!bookOpt.isPresent()) {
            return "redirect:/books";
        }
        
        List<Author> authors = authorService.findAll();
        List<Category> categories = categoryService.findAll();
        
        model.addAttribute("book", bookOpt.get());
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        model.addAttribute("bookStatuses", BookStatus.values());
        
        return "book-form";
    }

    /**
     * Kitap güncelleme işlemi
     * @param id Kitap ID'si
     * @param book Kitap
     * @param redirectAttributes Redirect attributes
     * @return Redirect
     */
    @PostMapping("/books/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        log.info("Kitap güncelleniyor, ID: {}", id);
        
        try {
            book.setId(id);
            Book updatedBook = bookService.save(book);
            redirectAttributes.addFlashAttribute("success", "Kitap başarıyla güncellendi: " + updatedBook.getTitle());
            return "redirect:/books";
        } catch (Exception e) {
            log.error("Kitap güncellenirken hata oluştu", e);
            redirectAttributes.addFlashAttribute("error", "Kitap güncellenirken hata oluştu: " + e.getMessage());
            return "redirect:/books/edit/" + id;
        }
    }

    /**
     * Kitap silme işlemi
     * @param id Kitap ID'si
     * @param redirectAttributes Redirect attributes
     * @return Redirect
     */
    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Kitap siliniyor, ID: {}", id);
        
        try {
            Optional<Book> bookOpt = bookService.findById(id);
            if (bookOpt.isPresent()) {
                String bookTitle = bookOpt.get().getTitle();
                bookService.deleteById(id);
                redirectAttributes.addFlashAttribute("success", "Kitap başarıyla silindi: " + bookTitle);
            } else {
                redirectAttributes.addFlashAttribute("error", "Kitap bulunamadı");
            }
        } catch (Exception e) {
            log.error("Kitap silinirken hata oluştu", e);
            redirectAttributes.addFlashAttribute("error", "Kitap silinirken hata oluştu: " + e.getMessage());
        }
        
        return "redirect:/books";
    }
} 