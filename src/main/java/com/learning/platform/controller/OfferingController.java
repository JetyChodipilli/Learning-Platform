package com.learning.platform.controller;

import com.learning.platform.dto.OfferingCreateRequest;
import com.learning.platform.dto.OfferingResponse;
import com.learning.platform.service.OfferingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for managing course offerings.
 * Teachers create offerings; parents browse them.
 */
@RestController
@RequestMapping("/api/v1/offerings")
@CrossOrigin(origins = "*")
public class OfferingController {

    private final OfferingService offeringService;

    public OfferingController(OfferingService offeringService) {
        this.offeringService = offeringService;
    }

    @PostMapping
    public ResponseEntity<OfferingResponse> createOffering(@Valid @RequestBody OfferingCreateRequest request) {
        OfferingResponse response = offeringService.createOffering(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<OfferingResponse>> getAllOfferings() {
        return ResponseEntity.ok(offeringService.getAllOfferings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferingResponse> getOfferingById(@PathVariable Long id) {
        return ResponseEntity.ok(offeringService.getOfferingById(id));
    }
}
