package com.library.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Kitap entity'si
 * 
 * Bu sınıf BaseEntity'den kalıtım alır ve kitap bilgilerini yönetir.
 * Generic programlama ve Collections Framework kullanımını gösterir.
 */
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @NotBlank(message = "Kitap başlığı boş olamaz")
    @Size(min = 1, max = 200, message = "Kitap başlığı 1-200 karakter arasında olmalıdır")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "ISBN boş olamaz")
    @Size(min = 10, max = 13, message = "ISBN 10-13 karakter arasında olmalıdır")
    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @Size(max = 1000, message = "Açıklama 1000 karakterden uzun olamaz")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Positive(message = "Sayfa sayısı pozitif olmalıdır")
    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "language")
    private String language;

    @Positive(message = "Fiyat pozitif olmalıdır")
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Kitap durumu belirtilmelidir")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;

    @Column(name = "location")
    private String location; // Kütüphanedeki konum (Raf, Bölüm vs.)

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    // Many-to-One ilişki: Bir kitabın bir yazarı olabilir
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    // Many-to-Many ilişki: Bir kitabın birden fazla kategorisi olabilir
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_categories",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    // One-to-Many ilişki: Bir kitabın birden fazla ödünç alma kaydı olabilir
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    // One-to-Many ilişki: Bir kitabın birden fazla rezervasyonu olabilir
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    // Getter ve Setter metodları
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public void setBorrowRecords(List<BorrowRecord> borrowRecords) {
        this.borrowRecords = borrowRecords;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Kitabın ödünç alınabilir olup olmadığını kontrol eder
     * @return Ödünç alınabilirse true
     */
    public boolean isAvailableForBorrow() {
        return status.isAvailableForBorrow();
    }

    /**
     * Kitabın rezerve edilebilir olup olmadığını kontrol eder
     * @return Rezerve edilebilirse true
     */
    public boolean isAvailableForReservation() {
        return status.isAvailableForReservation();
    }

    /**
     * Kitabın aktif ödünç alma kaydı var mı kontrol eder
     * @return Aktif ödünç alma kaydı varsa true
     */
    public boolean hasActiveBorrowRecord() {
        return borrowRecords.stream()
                .anyMatch(record -> record.getReturnDate() == null);
    }

    /**
     * Kitabın kategorilerini string olarak döndürür
     * @return Kategori isimleri virgülle ayrılmış string
     */
    public String getCategoryNames() {
        return categories.stream()
                .map(Category::getName)
                .reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);
    }

    /**
     * Kitabın yaşını hesaplar
     * @return Kitabın yaşı (yıl)
     */
    public int getAge() {
        if (publicationYear == null) {
            return 0;
        }
        return LocalDate.now().getYear() - publicationYear;
    }

    /**
     * Kitabın eski olup olmadığını kontrol eder (10 yıldan eski)
     * @return Eski ise true
     */
    public boolean isOldBook() {
        return getAge() > 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return isbn != null ? isbn.equals(book.isbn) : book.isbn == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        return result;
    }
} 