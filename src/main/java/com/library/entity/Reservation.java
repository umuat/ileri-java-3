package com.library.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Rezervasyon entity'si
 * 
 * Bu sınıf BaseEntity'den kalıtım alır ve kitap rezervasyonlarını yönetir.
 */
@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    @NotNull(message = "Kitap belirtilmelidir")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull(message = "Üye belirtilmelidir")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @NotNull(message = "Rezervasyon tarihi belirtilmelidir")
    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "fulfilled_date")
    private LocalDate fulfilledDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(name = "notes")
    private String notes;

    /**
     * Varsayılan rezervasyon geçerlilik süresi (gün)
     */
    private static final int DEFAULT_RESERVATION_DAYS = 7;

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

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getFulfilledDate() {
        return fulfilledDate;
    }

    public void setFulfilledDate(LocalDate fulfilledDate) {
        this.fulfilledDate = fulfilledDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Varsayılan expiry date'i hesaplar
     */
    public void calculateDefaultExpiryDate() {
        if (reservationDate != null && expiryDate == null) {
            expiryDate = reservationDate.plusDays(DEFAULT_RESERVATION_DAYS);
        }
    }

    /**
     * Rezervasyonun aktif olup olmadığını kontrol eder
     * @return Aktif ise true
     */
    public boolean isActive() {
        return status == ReservationStatus.PENDING && 
               (expiryDate == null || LocalDate.now().isBefore(expiryDate));
    }

    /**
     * Rezervasyonun süresi dolmuş olup olmadığını kontrol eder
     * @return Süresi dolmuşsa true
     */
    public boolean isExpired() {
        return expiryDate != null && LocalDate.now().isAfter(expiryDate);
    }

    /**
     * Rezervasyonun yerine getirilip getirilmediğini kontrol eder
     * @return Yerine getirildiyse true
     */
    public boolean isFulfilled() {
        return status == ReservationStatus.FULFILLED;
    }

    /**
     * Kalan gün sayısını hesaplar
     * @return Kalan gün sayısı (negatif ise süresi dolmuş)
     */
    public long getRemainingDays() {
        if (expiryDate == null) {
            return DEFAULT_RESERVATION_DAYS;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }

    /**
     * Rezervasyonu yerine getirir
     */
    public void fulfill() {
        this.status = ReservationStatus.FULFILLED;
        this.fulfilledDate = LocalDate.now();
    }

    /**
     * Rezervasyonu iptal eder
     */
    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }

    /**
     * Rezervasyonu süresi dolmuş olarak işaretler
     */
    public void expire() {
        this.status = ReservationStatus.EXPIRED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Reservation that = (Reservation) o;
        return book != null ? book.equals(that.book) : that.book == null &&
               member != null ? member.equals(that.member) : that.member == null &&
               reservationDate != null ? reservationDate.equals(that.reservationDate) : that.reservationDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (member != null ? member.hashCode() : 0);
        result = 31 * result + (reservationDate != null ? reservationDate.hashCode() : 0);
        return result;
    }
} 