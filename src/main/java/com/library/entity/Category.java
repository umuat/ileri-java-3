package com.library.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Kategori entity'si
 * 
 * Bu sınıf BaseEntity'den kalıtım alır ve kitap kategorilerini yönetir.
 */
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @NotBlank(message = "Kategori adı boş olamaz")
    @Size(min = 2, max = 50, message = "Kategori adı 2-50 karakter arasında olmalıdır")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Size(max = 200, message = "Açıklama 200 karakterden uzun olamaz")
    @Column(name = "description")
    private String description;

    @Column(name = "color_code")
    private String colorCode; // UI'da kategori rengi için

    // Many-to-Many ilişki: Bir kategorinin birden fazla kitabı olabilir
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    // Getter ve Setter metodları
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /**
     * Kategorideki kitap sayısını döndürür
     * @return Kitap sayısı
     */
    public int getBookCount() {
        return books != null ? books.size() : 0;
    }

    /**
     * Kategorideki mevcut kitap sayısını döndürür
     * @return Mevcut kitap sayısı
     */
    public long getAvailableBookCount() {
        return books != null ? books.stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
                .count() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return name != null ? name.equals(category.name) : category.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
} 