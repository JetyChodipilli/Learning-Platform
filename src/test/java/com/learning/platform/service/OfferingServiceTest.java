package com.learning.platform.service;

import com.learning.platform.domain.AppUser;
import com.learning.platform.domain.Course;
import com.learning.platform.domain.Offering;
import com.learning.platform.dto.OfferingCreateRequest;
import com.learning.platform.dto.OfferingResponse;
import com.learning.platform.exception.ResourceNotFoundException;
import com.learning.platform.mapper.OfferingMapper;
import com.learning.platform.repository.AppUserRepository;
import com.learning.platform.repository.CourseRepository;
import com.learning.platform.repository.OfferingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferingServiceTest {

    @Mock
    private OfferingRepository offeringRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private OfferingMapper offeringMapper;

    @InjectMocks
    private OfferingService offeringService;

    private Course testCourse;
    private AppUser testTeacher;

    @BeforeEach
    void setUp() {
        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setTitle("Test Course");

        testTeacher = new AppUser();
        testTeacher.setId(2L);
        testTeacher.setRole("TEACHER");
    }

    @Test
    void testCreateOffering_Success() {
        OfferingCreateRequest request = new OfferingCreateRequest();
        request.setCourseId(1L);
        request.setTeacherId(2L);
        request.setTitle("New Batch");
        request.setMaxCapacity(10);
        request.setTimeZone("UTC");

        OfferingCreateRequest.SessionRequest sr = new OfferingCreateRequest.SessionRequest();
        sr.setStartLocal(LocalDateTime.now().plusDays(1));
        sr.setEndLocal(LocalDateTime.now().plusDays(1).plusHours(1));
        request.setSessions(List.of(sr));

        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));
        when(appUserRepository.findById(2L)).thenReturn(Optional.of(testTeacher));
        
        Offering savedOffering = new Offering();
        savedOffering.setId(10L);
        when(offeringRepository.save(any(Offering.class))).thenReturn(savedOffering);
        
        OfferingResponse responseMock = new OfferingResponse();
        responseMock.setId(10L);
        when(offeringMapper.toOfferingResponse(savedOffering)).thenReturn(responseMock);

        OfferingResponse result = offeringService.createOffering(request);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(offeringRepository, times(1)).save(any(Offering.class));
    }

    @Test
    void testCreateOffering_CourseNotFound() {
        OfferingCreateRequest request = new OfferingCreateRequest();
        request.setCourseId(99L);
        request.setTeacherId(2L);

        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> offeringService.createOffering(request));
    }

    @Test
    void testGetAllCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(testCourse));

        List<Course> courses = offeringService.getAllCourses();

        assertEquals(1, courses.size());
        assertEquals("Test Course", courses.get(0).getTitle());
    }
}
