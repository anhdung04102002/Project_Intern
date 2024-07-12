package com.example.jwtspringsecurity.controller.Manager;

import com.example.jwtspringsecurity.enities.RequestLeave;
import com.example.jwtspringsecurity.enities.RequestType;
import com.example.jwtspringsecurity.enities.SubRequestType;
import com.example.jwtspringsecurity.services.managerService.RequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/manager")
public class ManageRequestController {
    @Autowired
    private RequestService requestService;
    @GetMapping("/request_pending")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Page<RequestLeave>> getAllPendingRequests(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<RequestLeave> pageResult = requestService.getAllRequest(page, size);
        return ResponseEntity.ok(pageResult);
    }
    @PutMapping("/approve/{requestId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> approveRequest(@PathVariable Long requestId) {
        try {
            RequestLeave approvedRequest = requestService.approveRequest(requestId);
            return ResponseEntity.ok(approvedRequest);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
    @PutMapping("/reject/{requestId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> rejectRequest(@PathVariable Long requestId) {
        try {
            RequestLeave approvedRequest = requestService.rejectRequest(requestId);
            return ResponseEntity.ok(approvedRequest);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
    @GetMapping("/search")
    @PreAuthorize("hasRole('MANAGER')")
    public Page<RequestLeave> searchRequests(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return requestService.searchRequest(username, email, page, size);
    }
    @GetMapping("/searchByRequestType")
    @PreAuthorize("hasRole('MANAGER')")
    public Page<RequestLeave> searchRequestByType(
            @RequestParam(defaultValue = "ALL") RequestType requestType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return requestService.searchRequestByType(requestType, page, size);
    }
    @GetMapping("/searchAll")
    @PreAuthorize("hasRole('MANAGER')")
    public Page<RequestLeave> searchAllRequest(
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "ALL") RequestType requestType,
            @RequestParam(defaultValue = "ALL") SubRequestType subRequestType,
            @RequestParam(defaultValue = "PENDING") String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (month == null || month < 1 || month > 12) {
            month = LocalDate.now().getMonthValue(); // Set to current month if not provided or invalid
        }
        return requestService.searchRequestByYearMonthAndTypesAndStatus(month, requestType, subRequestType, status, page, size);
    }
}
