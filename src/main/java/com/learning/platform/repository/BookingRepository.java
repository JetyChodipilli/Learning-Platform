package com.learning.platform.repository;

import com.learning.platform.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Bug Fix: Eagerly join fetch offering so that MapStruct can access
     * offering.title and offering.id without a LazyInitializationException
     * after the transaction ends.
     */
    @Query("SELECT b FROM Booking b JOIN FETCH b.offering JOIN FETCH b.parent WHERE b.parent.id = :parentId")
    List<Booking> findByParentId(@Param("parentId") Long parentId);
}
