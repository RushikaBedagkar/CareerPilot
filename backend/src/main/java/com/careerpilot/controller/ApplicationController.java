package com.careerpilot.controller;

import com.careerpilot.dto.ApplicationRequest;
import com.careerpilot.dto.ApplicationResponse;
import com.careerpilot.service.ApplicationService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> apply(
            @Valid @RequestBody ApplicationRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                applicationService.apply(
                        request,
                        email
                )
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponse>>
    getMyApplications(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                applicationService.getMyApplications(email)
        );
    }
    @GetMapping
    public ResponseEntity<List<ApplicationResponse>>
    getAllApplications(
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.getAllApplications()
        );
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.updateStatus(id, status)
        );
    }
}