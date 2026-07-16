package com.learning.platform.controller;

import com.learning.platform.dto.BookingRequest;
import com.learning.platform.dto.BookingResponse;
import com.learning.platform.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for managing bookings.
 * Parents book offerings and view their bookings.
 */
@RestController
@RequestMapping("/api/v1/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> bookOffering(@Valid @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.bookOffering(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookingsForParent(@RequestParam Long parentId) {
        return ResponseEntity.ok(bookingService.getBookingsForParent(parentId));
    }
}
