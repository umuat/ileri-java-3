package com.library.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Yazar entity'si
 * 
 * Bu sınıf BaseEntity'den kalıtım alarak temel alanları kazanır.
 * Yazar bilgilerini ve kitaplarıyla olan ilişkiyi yönetir.
 */
@Entity
@Table(name = "authors")
public class Author extends BaseEntity {

    @NotBlank(message = "Yazar adı boş olamaz")
    @Size(min = 2, max = 100, message = "Yazar adı 2-100 karakter arasında olmalıdır")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 500, message = "Biyografi 500 karakterden uzun olamaz")
    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    @Email(message = "Geçerli bir email adresi giriniz")
    @Column(name = "email")
    private String email;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "nationality")
    private String nationality;

    // One-to-Many ilişki: Bir yazarın birden fazla kitabı olabilir
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    // Getter ve Setter metodları
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Yazarın kitap sayısını döndürür
     * @return Kitap sayısı
     */
    public int getBookCount() {
        return books != null ? books.size() : 0;
    }

    /**
     * Yazarın aktif kitap sayısını döndürür (kütüphanede bulunan)
     * @return Aktif kitap sayısı
     */
    public long getActiveBookCount() {
        return books != null ? books.stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
                .count() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        return name != null ? name.equals(author.name) : author.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
} 