package com.library.entity;

/**
 * Rezervasyon durumlarını tanımlayan enum
 * 
 * Bu enum rezervasyonun mevcut durumunu belirtir.
 */
public enum ReservationStatus {
    
    PENDING("Beklemede", "Rezervasyon bekliyor"),
    FULFILLED("Yerine Getirildi", "Rezervasyon yerine getirildi"),
    CANCELLED("İptal Edildi", "Rezervasyon iptal edildi"),
    EXPIRED("Süresi Doldu", "Rezervasyon süresi doldu");

    private final String displayName;
    private final String description;

    ReservationStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Rezervasyonun aktif olup olmadığını kontrol eder
     * @return Aktif ise true
     */
    public boolean isActive() {
        return this == PENDING;
    }

    /**
     * Rezervasyonun tamamlanmış olup olmadığını kontrol eder
     * @return Tamamlanmışsa true
     */
    public boolean isCompleted() {
        return this == FULFILLED || this == CANCELLED || this == EXPIRED;
    }
} 