package com.library.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Üye entity'si
 * 
 * Bu sınıf BaseEntity'den kalıtım alır ve kütüphane üyelerini yönetir.
 */
@Entity
@Table(name = "members")
public class Member extends BaseEntity {

    @NotBlank(message = "Ad boş olamaz")
    @Size(min = 2, max = 50, message = "Ad 2-50 karakter arasında olmalıdır")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Soyad boş olamaz")
    @Size(min = 2, max = 50, message = "Soyad 2-50 karakter arasında olmalıdır")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Geçerli bir email adresi giriniz")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(min = 10, max = 15, message = "Telefon numarası 10-15 karakter arasında olmalıdır")
    @Column(name = "phone")
    private String phone;

    @Past(message = "Doğum tarihi geçmiş bir tarih olmalıdır")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "membership_number", unique = true)
    private String membershipNumber;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "membership_start_date")
    private LocalDate membershipStartDate;

    @Column(name = "membership_end_date")
    private LocalDate membershipEndDate;

    // One-to-Many ilişki: Bir üyenin birden fazla ödünç alma kaydı olabilir
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    // One-to-Many ilişki: Bir üyenin birden fazla rezervasyonu olabilir
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    // Getter ve Setter metodları
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(LocalDate membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public LocalDate getMembershipEndDate() {
        return membershipEndDate;
    }

    public void setMembershipEndDate(LocalDate membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
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
     * Üyenin tam adını döndürür
     * @return Tam ad
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Üyenin yaşını hesaplar
     * @return Yaş
     */
    public int getAge() {
        if (birthDate == null) {
            return 0;
        }
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    /**
     * Üyenin aktif ödünç alma kaydı sayısını döndürür
     * @return Aktif ödünç alma sayısı
     */
    public long getActiveBorrowCount() {
        return borrowRecords.stream()
                .filter(record -> record.getReturnDate() == null)
                .count();
    }

    /**
     * Üyenin üyelik durumunu kontrol eder
     * @return Aktif ise true
     */
    public boolean isMembershipActive() {
        if (!isActive) {
            return false;
        }
        if (membershipEndDate == null) {
            return true;
        }
        return LocalDate.now().isBefore(membershipEndDate) || LocalDate.now().isEqual(membershipEndDate);
    }

    /**
     * Üyenin üyelik süresini gün olarak hesaplar
     * @return Üyelik süresi (gün)
     */
    public long getMembershipDays() {
        if (membershipStartDate == null) {
            return 0;
        }
        LocalDate endDate = membershipEndDate != null ? membershipEndDate : LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(membershipStartDate, endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Member member = (Member) o;
        return email != null ? email.equals(member.email) : member.email == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
} 