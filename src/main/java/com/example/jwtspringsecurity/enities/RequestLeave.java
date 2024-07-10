package com.example.jwtspringsecurity.enities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "request_leave")
@Data
public class RequestLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RequestType requestType;
    @Enumerated(EnumType.STRING)
    private SubRequestType subRequestType;
//    @NotNull(message = "Date is required")
    private LocalDate date;
    private String status;
    private int timeOff;
    private String reason;
    private boolean early_or_late; // true là về sớm, false là đi muộn
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "leave_type_id", nullable = true)
    private LeaveType leaveType;
}
