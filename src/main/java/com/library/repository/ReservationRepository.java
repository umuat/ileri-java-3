package com.library.repository;

import com.library.entity.Reservation;
import com.library.entity.ReservationStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Rezervasyon Repository Interface
 */
@Repository
public interface ReservationRepository extends GenericRepository<Reservation, Long> {

    List<Reservation> findByMemberId(Long memberId);
    
    List<Reservation> findByBookId(Long bookId);
    
    List<Reservation> findByStatus(ReservationStatus status);
    
    @Query("SELECT r FROM Reservation r WHERE r.status = 'PENDING' AND (r.expiryDate IS NULL OR r.expiryDate > :currentDate)")
    List<Reservation> findActiveReservations(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT r FROM Reservation r WHERE r.expiryDate < :currentDate AND r.status = 'PENDING'")
    List<Reservation> findExpiredReservations(@Param("currentDate") LocalDate currentDate);
} 