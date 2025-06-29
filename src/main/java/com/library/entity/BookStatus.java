package com.library.entity;

/**
 * Kitap durumlarını tanımlayan enum
 * 
 * Bu enum kitabın kütüphanedeki mevcut durumunu belirtir.
 */
public enum BookStatus {
    
    AVAILABLE("Mevcut", "Kitap kütüphanede mevcut ve ödünç alınabilir"),
    BORROWED("Ödünç Verildi", "Kitap şu anda ödünç verilmiş durumda"),
    RESERVED("Rezerve Edildi", "Kitap rezerve edilmiş durumda"),
    LOST("Kayıp", "Kitap kayıp olarak işaretlenmiş"),
    DAMAGED("Hasarlı", "Kitap hasarlı durumda"),
    UNDER_MAINTENANCE("Bakımda", "Kitap bakım altında");

    private final String displayName;
    private final String description;

    BookStatus(String displayName, String description) {
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
     * Kitabın ödünç alınabilir olup olmadığını kontrol eder
     * @return Ödünç alınabilirse true
     */
    public boolean isAvailableForBorrow() {
        return this == AVAILABLE;
    }

    /**
     * Kitabın rezerve edilebilir olup olmadığını kontrol eder
     * @return Rezerve edilebilirse true
     */
    public boolean isAvailableForReservation() {
        return this == AVAILABLE || this == BORROWED;
    }
} 