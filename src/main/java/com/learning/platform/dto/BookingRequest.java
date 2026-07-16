package com.learning.platform.dto;

import jakarta.validation.constraints.NotNull;

public class BookingRequest {
    @NotNull
    private Long offeringId;
    
    @NotNull
    private Long parentId;

    public Long getOfferingId() { return offeringId; }
    public void setOfferingId(Long offeringId) { this.offeringId = offeringId; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}
