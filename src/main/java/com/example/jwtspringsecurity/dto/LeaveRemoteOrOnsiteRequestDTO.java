package com.example.jwtspringsecurity.dto;

import com.example.jwtspringsecurity.enities.RequestType;
import com.example.jwtspringsecurity.enities.SubRequestType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LeaveRemoteOrOnsiteRequestDTO {
    @Enumerated(EnumType.STRING)

    private SubRequestType subRequestType;
    @NotNull
    private LocalDate date;
    private String status;
    private String reason;

    public SubRequestType getSubRequestType() {
        return subRequestType;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public void setSubRequestType(SubRequestType subRequestType) {
        this.subRequestType = subRequestType;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
