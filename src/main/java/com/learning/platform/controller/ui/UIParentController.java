package com.learning.platform.controller.ui;

import com.learning.platform.dto.BookingRequest;
import com.learning.platform.service.BookingService;
import com.learning.platform.service.OfferingService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.platform.domain.AppUser;
import com.learning.platform.repository.AppUserRepository;
import java.security.Principal;

@Controller
@RequestMapping("/ui/parent")
public class UIParentController {

    private final OfferingService offeringService;
    private final BookingService bookingService;
    private final AppUserRepository appUserRepository;

    public UIParentController(OfferingService offeringService, BookingService bookingService, AppUserRepository appUserRepository) {
        this.offeringService = offeringService;
        this.bookingService = bookingService;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        AppUser user = appUserRepository.findByEmail(principal.getName()).orElseThrow();
        Long id = user.getId();
        
        model.addAttribute("parentId", id);
        model.addAttribute("offerings", offeringService.getAllOfferings());
        model.addAttribute("bookings", bookingService.getBookingsForParent(id));
        return "parent-dashboard";
    }

    @PostMapping("/book")
    public String bookOffering(Principal principal,
                               @RequestParam Long offeringId,
                               RedirectAttributes redirectAttributes) {
        AppUser user = appUserRepository.findByEmail(principal.getName()).orElseThrow();
        Long id = user.getId();
        
        try {
            BookingRequest request = new BookingRequest();
            request.setParentId(id);
            request.setOfferingId(offeringId);
            bookingService.bookOffering(request);
            redirectAttributes.addFlashAttribute("success", "Successfully booked the class!");
        } catch (DataIntegrityViolationException e) {
            String rootMsg = e.getMostSpecificCause().getMessage();
            if (rootMsg != null && rootMsg.contains("no_overlap")) {
                redirectAttributes.addFlashAttribute("error", "Booking failed: Your schedule overlaps with an existing booking.");
            } else if (rootMsg != null && rootMsg.contains("uq_booking_parent_offering")) {
                redirectAttributes.addFlashAttribute("error", "You have already booked this offering!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Booking failed: Database constraint violated.");
            }
        } catch (Exception e) {
            String msg = (e.getMessage() != null) ? e.getMessage() : "An unexpected error occurred.";
            redirectAttributes.addFlashAttribute("error", "Booking failed: " + msg);
        }
        return "redirect:/ui/parent/dashboard";
    }
}
