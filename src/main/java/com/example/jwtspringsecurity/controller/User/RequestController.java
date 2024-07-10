package com.example.jwtspringsecurity.controller.User;

import com.example.jwtspringsecurity.dto.LeaveOffRequestDT0;
import com.example.jwtspringsecurity.dto.LeaveRemoteOrOnsiteRequestDTO;
import com.example.jwtspringsecurity.enities.RequestLeave;
import com.example.jwtspringsecurity.services.UserService.LeaveOffRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class RequestController {
    @Autowired
    private LeaveOffRequestService leaveOffRequestService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/leaveOffRequest")
    public ResponseEntity<?> addLeaveOffRequest(@Valid @RequestBody LeaveOffRequestDT0 leaveOffRequestDT0) {
        try {
            RequestLeave savedLeaveOffRequest = leaveOffRequestService.saveLeaveOffRequest(leaveOffRequestDT0);
            return ResponseEntity.ok(savedLeaveOffRequest);


        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/leaveOnsiteRequest")
    public ResponseEntity<?> addLeaveOnsiteRequest(@Valid @RequestBody LeaveRemoteOrOnsiteRequestDTO leaveRemoteOrOnsiteRequestDTO) {
        try {
            RequestLeave savedLeaveOnsiteRequest = leaveOffRequestService.saveLeaveOnsiteRequest(leaveRemoteOrOnsiteRequestDTO);
            return ResponseEntity.ok(savedLeaveOnsiteRequest);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/leaveRemoteRequest")
    public ResponseEntity<?> addLeaveRemoteRequest(@Valid @RequestBody LeaveRemoteOrOnsiteRequestDTO leaveRemoteOrOnsiteRequestDTO) {
        try {
            RequestLeave savedLeaveOnsiteRequest = leaveOffRequestService.saveLeaveRemoteRequest(leaveRemoteOrOnsiteRequestDTO);
            return ResponseEntity.ok(savedLeaveOnsiteRequest);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}


