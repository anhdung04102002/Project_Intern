package com.example.jwtspringsecurity.controller.User;

import com.example.jwtspringsecurity.dto.LeaveOffRequestDT0;
import com.example.jwtspringsecurity.dto.LeaveRemoteOrOnsiteRequestDTO;
import com.example.jwtspringsecurity.enities.RequestLeave;
import com.example.jwtspringsecurity.enities.RequestType;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.repositories.UserRepo;
import com.example.jwtspringsecurity.services.UserService.LeaveOffRequestService;
import com.example.jwtspringsecurity.services.UserService.LeaveOffRequestServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/user")
public class RequestController {
    @Autowired
    private LeaveOffRequestService leaveOffRequestService;
    @Autowired
    private UserRepo userRepository;

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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/searchRequest")
    public ResponseEntity<?> searchMyRequests(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "ALL") RequestType requestType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            if (month == null) {
                month = LocalDate.now().getMonthValue(); // Set to current month if not provided or invalid
            }
            if (year == null) {
                year = LocalDate.now().getYear();
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User currentUser = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
            Long userId = currentUser.getId();

            Page<RequestLeave> requests = leaveOffRequestService.searchRequestByUserAndMonthAndRequestType(year, month, requestType, userId, page, size);
            return ResponseEntity.ok(requests);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


}


