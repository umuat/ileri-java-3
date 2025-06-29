package com.library.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Ödünç alma kaydı entity'si
 * 
 * Bu sınıf BaseEntity'den kalıtım alır ve kitap ödünç alma işlemlerini yönetir.
 */
@Entity
@Table(name = "borrow_records")
public class BorrowRecord extends BaseEntity {

    @NotNull(message = "Kitap belirtilmelidir")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull(message = "Üye belirtilmelidir")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @NotNull(message = "Ödünç alma tarihi belirtilmelidir")
    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "fine_amount")
    private Double fineAmount = 0.0;

    @Column(name = "notes")
    private String notes;

    /**
     * Varsayılan ödünç alma süresi (gün)
     */
    private static final int DEFAULT_BORROW_DAYS = 14;

    /**
     * Günlük ceza miktarı (TL)
     */
    private static final double DAILY_FINE_AMOUNT = 1.0;

    // Getter ve Setter metodları
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Varsayılan due date'i hesaplar
     */
    public void calculateDefaultDueDate() {
        if (borrowDate != null && dueDate == null) {
            dueDate = borrowDate.plusDays(DEFAULT_BORROW_DAYS);
        }
    }

    /**
     * Kitabın iade edilip edilmediğini kontrol eder
     * @return İade edildiyse true
     */
    public boolean isReturned() {
        return returnDate != null;
    }

    /**
     * Kitabın gecikmeli olup olmadığını kontrol eder
     * @return Gecikmeli ise true
     */
    public boolean isOverdue() {
        if (isReturned()) {
            return returnDate.isAfter(dueDate);
        }
        return LocalDate.now().isAfter(dueDate);
    }

    /**
     * Gecikme gün sayısını hesaplar
     * @return Gecikme gün sayısı
     */
    public long getOverdueDays() {
        if (!isOverdue()) {
            return 0;
        }
        LocalDate endDate = isReturned() ? returnDate : LocalDate.now();
        return ChronoUnit.DAYS.between(dueDate, endDate);
    }

    /**
     * Ceza miktarını hesaplar
     * @return Ceza miktarı
     */
    public double calculateFine() {
        if (!isOverdue()) {
            return 0.0;
        }
        long overdueDays = getOverdueDays();
        return overdueDays * DAILY_FINE_AMOUNT;
    }

    /**
     * Kitabı iade eder
     */
    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.fineAmount = calculateFine();
        
        // Kitabın durumunu güncelle
        if (this.book != null) {
            this.book.setStatus(BookStatus.AVAILABLE);
        }
    }

    /**
     * Ödünç alma süresini gün olarak hesaplar
     * @return Ödünç alma süresi (gün)
     */
    public long getBorrowDuration() {
        LocalDate endDate = isReturned() ? returnDate : LocalDate.now();
        return ChronoUnit.DAYS.between(borrowDate, endDate);
    }

    /**
     * Kalan gün sayısını hesaplar
     * @return Kalan gün sayısı (negatif ise gecikme)
     */
    public long getRemainingDays() {
        if (isReturned()) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BorrowRecord that = (BorrowRecord) o;
        return book != null ? book.equals(that.book) : that.book == null &&
               member != null ? member.equals(that.member) : that.member == null &&
               borrowDate != null ? borrowDate.equals(that.borrowDate) : that.borrowDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (member != null ? member.hashCode() : 0);
        result = 31 * result + (borrowDate != null ? borrowDate.hashCode() : 0);
        return result;
    }
} 