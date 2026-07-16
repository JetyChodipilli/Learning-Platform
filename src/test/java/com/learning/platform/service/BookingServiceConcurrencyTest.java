package com.learning.platform.service;

import com.learning.platform.domain.AppUser;
import com.learning.platform.domain.Course;
import com.learning.platform.domain.Offering;
import com.learning.platform.domain.Session;
import com.learning.platform.dto.BookingRequest;
import com.learning.platform.exception.CapacityFullException;
import com.learning.platform.repository.AppUserRepository;
import com.learning.platform.repository.CourseRepository;
import com.learning.platform.repository.OfferingRepository;
import com.learning.platform.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class BookingServiceConcurrencyTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private BookingService bookingService;

    @Autowired
    private OfferingRepository offeringRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private Course testCourse;
    private AppUser testTeacher;

    @BeforeEach
    void setUp() {
        // Always create fresh test data — avoids stale ID references across tests
        testCourse = new Course();
        testCourse.setTitle("Test Course");
        courseRepository.save(testCourse);

        testTeacher = new AppUser();
        testTeacher.setRole("TEACHER");
        testTeacher.setTimezone("UTC");
        appUserRepository.save(testTeacher);
    }

    /**
     * Scenario A: 100 concurrent parents try to book 1 offering with capacity 10.
     * Expected: exactly 10 succeed, at least 90 are rejected with CapacityFullException.
     */
    @Test
    void testConcurrentCapacityBooking() throws InterruptedException {
        // Bug Fix: Offering must have at least one Session or BookingService
        // throws IllegalStateException("Offering has no sessions defined").
        Instant sessionStart = Instant.now().plus(2, ChronoUnit.DAYS);
        Instant sessionEnd   = sessionStart.plus(1, ChronoUnit.HOURS);

        Offering offering = new Offering();
        offering.setCourse(testCourse);
        offering.setTeacher(testTeacher);
        offering.setTitle("High Demand Batch");
        offering.setMaxCapacity(10);
        offering.setCurrentCapacity(0);
        offering = offeringRepository.save(offering);

        // Save session linked to offering
        Session session = new Session();
        session.setOffering(offering);
        session.setStartTime(sessionStart);
        session.setEndTime(sessionEnd);
        sessionRepository.save(session);

        // Create 100 distinct parent users
        List<Long> parentIds = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            AppUser parent = new AppUser();
            parent.setRole("PARENT");
            parent.setTimezone("UTC");
            appUserRepository.save(parent);
            parentIds.add(parent.getId());
        }

        ExecutorService executor = Executors.newFixedThreadPool(100);
        CountDownLatch startGate = new CountDownLatch(1);   // releases all threads simultaneously
        CountDownLatch doneLatch = new CountDownLatch(100);

        AtomicInteger successCount     = new AtomicInteger(0);
        AtomicInteger capacityFullCount = new AtomicInteger(0);
        AtomicInteger otherErrorCount  = new AtomicInteger(0);

        final Long offeringId = offering.getId();

        for (int i = 0; i < 100; i++) {
            final Long parentId = parentIds.get(i);
            executor.submit(() -> {
                try {
                    startGate.await(); // all threads wait here, then released simultaneously
                    BookingRequest request = new BookingRequest();
                    request.setOfferingId(offeringId);
                    request.setParentId(parentId);
                    bookingService.bookOffering(request);
                    successCount.incrementAndGet();
                } catch (CapacityFullException e) {
                    capacityFullCount.incrementAndGet();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    otherErrorCount.incrementAndGet();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startGate.countDown();                          // release all 100 threads at once
        boolean completed = doneLatch.await(30, TimeUnit.SECONDS);
        assertThat(completed).as("All threads should finish within 30 seconds").isTrue();

        // Core assertions
        assertThat(successCount.get()).isEqualTo(10);
        assertThat(capacityFullCount.get()).isGreaterThanOrEqualTo(90);
        assertThat(otherErrorCount.get()).isZero();

        // Verify DB state
        Offering updated = offeringRepository.findById(offeringId).orElseThrow();
        assertThat(updated.getCurrentCapacity()).isEqualTo(10);

        executor.shutdown();
    }

    /**
     * Scenario C: Same parent tries to book two different Offerings with the exact
     * same session time simultaneously. Only 1 should succeed; the other must fail
     * with a schedule conflict (DataIntegrityViolationException from the no_overlap exclusion).
     */
    @Test
    void testConcurrentOverlappingSchedules() throws InterruptedException {
        AppUser parent = new AppUser();
        parent.setRole("PARENT");
        parent.setTimezone("UTC");
        appUserRepository.save(parent);

        Instant start = Instant.now().plus(3, ChronoUnit.DAYS);
        Instant end   = start.plus(1, ChronoUnit.HOURS);

        // Offering 1 with session
        Offering o1 = new Offering();
        o1.setCourse(testCourse);
        o1.setTeacher(testTeacher);
        o1.setTitle("Batch A");
        o1.setMaxCapacity(10);
        o1.setCurrentCapacity(0);
        o1 = offeringRepository.save(o1);

        Session s1 = new Session();
        s1.setStartTime(start);
        s1.setEndTime(end);
        s1.setOffering(o1);
        sessionRepository.save(s1);

        // Offering 2 with overlapping session
        Offering o2 = new Offering();
        o2.setCourse(testCourse);
        o2.setTeacher(testTeacher);
        o2.setTitle("Batch B");
        o2.setMaxCapacity(10);
        o2.setCurrentCapacity(0);
        o2 = offeringRepository.save(o2);

        Session s2 = new Session();
        s2.setStartTime(start);
        s2.setEndTime(end);
        s2.setOffering(o2);
        sessionRepository.save(s2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch startGate  = new CountDownLatch(1);
        CountDownLatch doneLatch  = new CountDownLatch(2);

        AtomicInteger successCount  = new AtomicInteger(0);
        AtomicInteger conflictCount = new AtomicInteger(0);

        final Long o1Id      = o1.getId();
        final Long o2Id      = o2.getId();
        final Long parentId  = parent.getId();

        Runnable bookO1 = () -> {
            try {
                startGate.await();
                BookingRequest r = new BookingRequest();
                r.setOfferingId(o1Id);
                r.setParentId(parentId);
                bookingService.bookOffering(r);
                successCount.incrementAndGet();
            } catch (DataIntegrityViolationException e) {
                conflictCount.incrementAndGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception ignored) {
            } finally {
                doneLatch.countDown();
            }
        };

        Runnable bookO2 = () -> {
            try {
                startGate.await();
                BookingRequest r = new BookingRequest();
                r.setOfferingId(o2Id);
                r.setParentId(parentId);
                bookingService.bookOffering(r);
                successCount.incrementAndGet();
            } catch (DataIntegrityViolationException e) {
                conflictCount.incrementAndGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception ignored) {
            } finally {
                doneLatch.countDown();
            }
        };

        executor.submit(bookO1);
        executor.submit(bookO2);

        startGate.countDown();
        boolean completed = doneLatch.await(15, TimeUnit.SECONDS);
        assertThat(completed).as("Both threads should finish within 15 seconds").isTrue();

        assertThat(successCount.get()).isEqualTo(1);
        assertThat(conflictCount.get()).isEqualTo(1);

        executor.shutdown();
    }
}