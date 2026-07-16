package com.learning.platform.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public class OfferingCreateRequest {

    @NotNull
    private Long courseId;

    @NotNull
    private Long teacherId;

    @NotBlank
    private String title;

    @Positive
    private Integer maxCapacity;

    @NotBlank
    private String timeZone; // e.g. "America/New_York"

    @NotNull
    private List<SessionRequest> sessions;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public List<SessionRequest> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionRequest> sessions) {
        this.sessions = sessions;
    }

    public static class SessionRequest {
        @NotNull
        @Future
        private LocalDateTime startLocal;

        @NotNull
        @Future
        private LocalDateTime endLocal;

        public LocalDateTime getStartLocal() {
            return startLocal;
        }

        public void setStartLocal(LocalDateTime startLocal) {
            this.startLocal = startLocal;
        }

        public LocalDateTime getEndLocal() {
            return endLocal;
        }

        public void setEndLocal(LocalDateTime endLocal) {
            this.endLocal = endLocal;
        }
    }
}
