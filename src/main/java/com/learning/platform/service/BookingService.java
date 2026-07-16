package com.learning.platform.service;

import com.learning.platform.domain.AppUser;
import com.learning.platform.domain.Booking;
import com.learning.platform.domain.Offering;
import com.learning.platform.domain.Session;
import com.learning.platform.dto.BookingRequest;
import com.learning.platform.dto.BookingResponse;
import com.learning.platform.exception.CapacityFullException;
import com.learning.platform.exception.ResourceNotFoundException;
import com.learning.platform.mapper.OfferingMapper;
import com.learning.platform.repository.AppUserRepository;
import com.learning.platform.repository.BookingRepository;
import com.learning.platform.repository.OfferingRepository;
import com.learning.platform.repository.ParentScheduleDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final OfferingRepository offeringRepository;
    private final AppUserRepository appUserRepository;
    private final ParentScheduleDao parentScheduleDao;
    private final OfferingMapper offeringMapper;

    public BookingService(BookingRepository bookingRepository, OfferingRepository offeringRepository,
                          AppUserRepository appUserRepository, ParentScheduleDao parentScheduleDao,
                          OfferingMapper offeringMapper) {
        this.bookingRepository = bookingRepository;
        this.offeringRepository = offeringRepository;
        this.appUserRepository = appUserRepository;
        this.parentScheduleDao = parentScheduleDao;
        this.offeringMapper = offeringMapper;
    }

    /**
     * Books an offering for a parent.
     *
     * Bug Fix #3: findByIdWithPessimisticWriteLock now uses JOIN FETCH o.sessions,
     * so offering.getSessions() is safe to iterate inside this transaction without
     * triggering a LazyInitializationException.
     *
     * Concurrent safety:
     *  - PESSIMISTIC_WRITE lock serializes capacity checks (Scenario A).
     *  - UNIQUE(parent_id, offering_id) prevents duplicate bookings (Scenario B).
     *  - PostgreSQL EXCLUDE USING gist prevents overlapping schedules (Scenario C & D).
     */
    @Transactional
    public BookingResponse bookOffering(BookingRequest request) {
        if (request.getOfferingId() == null || request.getParentId() == null) {
            throw new IllegalArgumentException("offeringId and parentId must not be null.");
        }

        // 1. Fetch Offering with PESSIMISTIC_WRITE lock + eagerly loaded sessions.
        Offering offering = offeringRepository.findByIdWithPessimisticWriteLock(request.getOfferingId())
                .orElseThrow(() -> new ResourceNotFoundException("Offering not found with id: " + request.getOfferingId()));

        if ("CANCELLED".equals(offering.getStatus())) {
            throw new IllegalArgumentException("Cannot book a cancelled offering.");
        }

        // 2. Capacity check — inside the lock so no race condition is possible.
        if (offering.getCurrentCapacity() >= offering.getMaxCapacity()) {
            throw new CapacityFullException("Offering '" + offering.getTitle() + "' has reached maximum capacity.");
        }

        // 3. Fetch Parent
        AppUser parent = appUserRepository.findById(request.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id: " + request.getParentId()));

        // 4. Create and persist Booking.
        //    The UNIQUE(parent_id, offering_id) DB constraint will reject duplicates at DB level
        //    if two requests sneak past the capacity check simultaneously.
        Booking booking = new Booking();
        booking.setParent(parent);
        booking.setOffering(offering);
        Booking savedBooking = bookingRepository.save(booking);

        // 5. Increment and persist capacity atomically inside the same transaction.
        offering.setCurrentCapacity(offering.getCurrentCapacity() + 1);
        offeringRepository.save(offering);

        // 6. Insert each session into parent_schedule.
        //    The EXCLUDE USING gist constraint on parent_schedule will throw a
        //    DataIntegrityViolationException if any session overlaps an existing one.
        //    Bug Fix #3: sessions are now eagerly loaded via JOIN FETCH, so no lazy proxy issues.
        List<Session> sessions = offering.getSessions();
        if (sessions == null || sessions.isEmpty()) {
            throw new IllegalStateException("Offering '" + offering.getTitle() + "' has no sessions defined.");
        }
        for (Session session : sessions) {
            parentScheduleDao.insertSchedule(
                    parent.getId(),
                    session.getId(),
                    session.getStartTime(),
                    session.getEndTime()
            );
        }

        return offeringMapper.toBookingResponse(savedBooking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsForParent(Long parentId) {
        if (parentId == null) {
            throw new IllegalArgumentException("parentId must not be null.");
        }
        return offeringMapper.toBookingResponseList(bookingRepository.findByParentId(parentId));
    }
}
