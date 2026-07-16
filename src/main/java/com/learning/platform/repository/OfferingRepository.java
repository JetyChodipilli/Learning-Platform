package com.learning.platform.repository;

import com.learning.platform.domain.Offering;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long> {

    /**
     * Pessimistic write lock on the offering row to serialize concurrent capacity updates.
     * Sessions are JOIN FETCHed eagerly to avoid LazyInitializationException when
     * BookingService iterates sessions inside the same transaction.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Offering o JOIN FETCH o.sessions WHERE o.id = :id")
    Optional<Offering> findByIdWithPessimisticWriteLock(@Param("id") Long id);

    /**
     * Eagerly fetch offerings with course, teacher, and sessions to prevent
     * LazyInitializationException in MapStruct mapping outside session scope.
     */
    @Query("SELECT DISTINCT o FROM Offering o " +
           "JOIN FETCH o.course " +
           "JOIN FETCH o.teacher " +
           "LEFT JOIN FETCH o.sessions")
    List<Offering> findAllWithDetails();
}
