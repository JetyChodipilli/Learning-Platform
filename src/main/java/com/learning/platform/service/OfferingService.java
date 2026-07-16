package com.learning.platform.service;

import com.learning.platform.domain.AppUser;
import com.learning.platform.domain.Course;
import com.learning.platform.domain.Offering;
import com.learning.platform.domain.Session;
import com.learning.platform.dto.OfferingCreateRequest;
import com.learning.platform.dto.OfferingResponse;
import com.learning.platform.exception.ResourceNotFoundException;
import com.learning.platform.mapper.OfferingMapper;
import com.learning.platform.repository.AppUserRepository;
import com.learning.platform.repository.CourseRepository;
import com.learning.platform.repository.OfferingRepository;
import com.learning.platform.repository.ParentScheduleDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OfferingService {

    private final OfferingRepository offeringRepository;
    private final CourseRepository courseRepository;
    private final AppUserRepository appUserRepository;
    private final OfferingMapper offeringMapper;
    private final ParentScheduleDao parentScheduleDao;

    public OfferingService(OfferingRepository offeringRepository, CourseRepository courseRepository,
                           AppUserRepository appUserRepository, OfferingMapper offeringMapper,
                           ParentScheduleDao parentScheduleDao) {
        this.offeringRepository = offeringRepository;
        this.courseRepository = courseRepository;
        this.appUserRepository = appUserRepository;
        this.offeringMapper = offeringMapper;
        this.parentScheduleDao = parentScheduleDao;
    }

    @Transactional
    public OfferingResponse createOffering(OfferingCreateRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + request.getCourseId()));

        AppUser teacher = appUserRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + request.getTeacherId()));

        if (request.getSessions() == null || request.getSessions().isEmpty()) {
            throw new IllegalArgumentException("At least one session must be provided.");
        }

        ZoneId zoneId;
        try {
            zoneId = ZoneId.of(request.getTimeZone());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid timezone: " + request.getTimeZone());
        }

        Offering offering = new Offering();
        offering.setCourse(course);
        offering.setTeacher(teacher);
        offering.setTitle(request.getTitle());
        offering.setMaxCapacity(request.getMaxCapacity());
        offering.setCurrentCapacity(0);

        for (OfferingCreateRequest.SessionRequest sr : request.getSessions()) {
            if (sr.getEndLocal().isBefore(sr.getStartLocal()) || sr.getEndLocal().isEqual(sr.getStartLocal())) {
                throw new IllegalArgumentException("Session end time must be after start time.");
            }
            Session session = new Session();
            session.setStartTime(ZonedDateTime.of(sr.getStartLocal(), zoneId).toInstant());
            session.setEndTime(ZonedDateTime.of(sr.getEndLocal(), zoneId).toInstant());
            offering.addSession(session);
        }

        Offering savedOffering = offeringRepository.save(offering);
        return offeringMapper.toOfferingResponse(savedOffering);
    }

    @Transactional(readOnly = true)
    public List<OfferingResponse> getAllOfferings() {
        return offeringMapper.toOfferingResponseList(offeringRepository.findAllWithDetails());
    }

    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OfferingResponse getOfferingById(Long id) {
        Offering offering = offeringRepository.findAllWithDetails().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Offering not found with id: " + id));
        return offeringMapper.toOfferingResponse(offering);
    }

    @Transactional
    public void cancelOffering(Long offeringId, Long teacherId) {
        Offering offering = offeringRepository.findById(offeringId)
                .orElseThrow(() -> new ResourceNotFoundException("Offering not found"));

        if (!offering.getTeacher().getId().equals(teacherId)) {
            throw new IllegalArgumentException("Only the assigned teacher can cancel this offering.");
        }

        offering.setStatus("CANCELLED");
        offeringRepository.save(offering);

        // Delete parent schedules so parents can book other classes at this time
        parentScheduleDao.deleteByOfferingId(offeringId);
    }
}
