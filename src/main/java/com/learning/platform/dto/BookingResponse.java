package com.learning.platform.dto;

import java.time.Instant;

public class BookingResponse {
    private Long id;
    private Long parentId;
    private Long offeringId;
    private String offeringTitle;
    private String offeringStatus;
    private Instant createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public Long getOfferingId() { return offeringId; }
    public void setOfferingId(Long offeringId) { this.offeringId = offeringId; }

    public String getOfferingTitle() {
        return offeringTitle;
    }

    public void setOfferingTitle(String offeringTitle) {
        this.offeringTitle = offeringTitle;
    }

    public String getOfferingStatus() {
        return offeringStatus;
    }

    public void setOfferingStatus(String offeringStatus) {
        this.offeringStatus = offeringStatus;
    }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
