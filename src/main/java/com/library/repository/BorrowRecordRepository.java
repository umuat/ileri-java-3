package com.library.repository;

import com.library.entity.BorrowRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Ödünç Alma Kaydı Repository Interface
 */
@Repository
public interface BorrowRecordRepository extends GenericRepository<BorrowRecord, Long> {

    List<BorrowRecord> findByMemberId(Long memberId);
    
    List<BorrowRecord> findByBookId(Long bookId);
    
    @Query("SELECT br FROM BorrowRecord br WHERE br.returnDate IS NULL")
    List<BorrowRecord> findActiveBorrowRecords();
    
    @Query("SELECT br FROM BorrowRecord br WHERE br.dueDate < :currentDate AND br.returnDate IS NULL")
    List<BorrowRecord> findOverdueRecords(@Param("currentDate") LocalDate currentDate);
} 