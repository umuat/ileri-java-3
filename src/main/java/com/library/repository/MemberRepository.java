package com.library.repository;

import com.library.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Üye Repository Interface
 */
@Repository
public interface MemberRepository extends GenericRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    
    Optional<Member> findByMembershipNumber(String membershipNumber);
    
    List<Member> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);
    
    List<Member> findByIsActive(boolean isActive);
    
    @Query("SELECT m FROM Member m WHERE m.membershipEndDate < :currentDate")
    List<Member> findExpiredMemberships(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT m FROM Member m WHERE m.membershipEndDate BETWEEN :startDate AND :endDate")
    List<Member> findMembershipsExpiringBetween(@Param("startDate") LocalDate startDate, 
                                               @Param("endDate") LocalDate endDate);
} 