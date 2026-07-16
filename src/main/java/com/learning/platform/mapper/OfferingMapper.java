package com.learning.platform.mapper;

import com.learning.platform.domain.Booking;
import com.learning.platform.domain.Offering;
import com.learning.platform.domain.Session;
import com.learning.platform.dto.BookingResponse;
import com.learning.platform.dto.OfferingResponse;
import com.learning.platform.dto.SessionResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OfferingMapper {

    public OfferingResponse toOfferingResponse(Offering offering) {
        if (offering == null) {
            return null;
        }

        OfferingResponse response = new OfferingResponse();
        response.setId(offering.getId());
        response.setTitle(offering.getTitle());
        response.setMaxCapacity(offering.getMaxCapacity());
        response.setCurrentCapacity(offering.getCurrentCapacity());
        response.setStatus(offering.getStatus());

        if (offering.getCourse() != null) {
            response.setCourseId(offering.getCourse().getId());
            response.setCourseTitle(offering.getCourse().getTitle());
        }

        if (offering.getTeacher() != null) {
            response.setTeacherId(offering.getTeacher().getId());
        }

        if (offering.getSessions() != null) {
            response.setSessions(offering.getSessions().stream()
                    .map(this::toSessionResponse)
                    .collect(Collectors.toList()));
        }

        return response;
    }

    public SessionResponse toSessionResponse(Session session) {
        if (session == null) {
            return null;
        }
        SessionResponse response = new SessionResponse();
        response.setId(session.getId());
        response.setStartTime(session.getStartTime());
        response.setEndTime(session.getEndTime());
        return response;
    }

    public List<OfferingResponse> toOfferingResponseList(List<Offering> offerings) {
        if (offerings == null) {
            return null;
        }
        return offerings.stream().map(this::toOfferingResponse).collect(Collectors.toList());
    }

    public BookingResponse toBookingResponse(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setCreatedAt(booking.getCreatedAt());

        if (booking.getParent() != null) {
            response.setParentId(booking.getParent().getId());
        }

        if (booking.getOffering() != null) {
            response.setOfferingId(booking.getOffering().getId());
            response.setOfferingTitle(booking.getOffering().getTitle());
            response.setOfferingStatus(booking.getOffering().getStatus());
        }

        return response;
    }

    public List<BookingResponse> toBookingResponseList(List<Booking> bookings) {
        if (bookings == null) {
            return null;
        }
        return bookings.stream().map(this::toBookingResponse).collect(Collectors.toList());
    }
}
