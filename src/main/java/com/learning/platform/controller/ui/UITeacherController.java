package com.learning.platform.controller.ui;

import com.learning.platform.dto.OfferingCreateRequest;
import com.learning.platform.service.OfferingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.platform.domain.AppUser;
import com.learning.platform.repository.AppUserRepository;
import java.security.Principal;

@Controller
@RequestMapping("/ui/teacher")
public class UITeacherController {

    private final OfferingService offeringService;
    private final AppUserRepository appUserRepository;

    public UITeacherController(OfferingService offeringService, AppUserRepository appUserRepository) {
        this.offeringService = offeringService;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        AppUser user = appUserRepository.findByEmail(principal.getName()).orElseThrow();
        Long id = user.getId();
        
        model.addAttribute("teacherId", id);
        var offerings = offeringService.getAllOfferings().stream()
                .filter(o -> o.getTeacherId().equals(id))
                .toList();
        model.addAttribute("offerings", offerings);
        model.addAttribute("courses", offeringService.getAllCourses());
        return "teacher-dashboard";
    }

    @PostMapping("/offering")
    public String createOffering(Principal principal,
                                 @ModelAttribute OfferingCreateRequest request,
                                 RedirectAttributes redirectAttributes) {
        AppUser user = appUserRepository.findByEmail(principal.getName()).orElseThrow();
        Long id = user.getId();
        
        try {
            request.setTeacherId(id);
            offeringService.createOffering(request);
            redirectAttributes.addFlashAttribute("success", "Offering published successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to publish: " + e.getMessage());
        }
        return "redirect:/ui/teacher/dashboard";
    }

    @PostMapping("/offering/{offeringId}/cancel")
    public String cancelOffering(Principal principal,
                                 @PathVariable Long offeringId,
                                 RedirectAttributes redirectAttributes) {
        AppUser user = appUserRepository.findByEmail(principal.getName()).orElseThrow();
        try {
            offeringService.cancelOffering(offeringId, user.getId());
            redirectAttributes.addFlashAttribute("success", "Course offering cancelled successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to cancel offering: " + e.getMessage());
        }
        return "redirect:/ui/teacher/dashboard";
    }
}
