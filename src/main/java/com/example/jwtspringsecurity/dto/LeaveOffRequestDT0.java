package com.example.jwtspringsecurity.dto;

import com.example.jwtspringsecurity.enities.RequestType;
import com.example.jwtspringsecurity.enities.SubRequestType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class LeaveOffRequestDT0 {

    @Enumerated(EnumType.STRING)

    private SubRequestType subRequestType;
    @NotNull(message = "Date is required")
    private LocalDate date;

    private int timeOff;
    private String reason;
    private Long leaveTypeId; // Field to specify the LeaveType
    private boolean early_or_late;

    public boolean isEarly_or_late() {
        return early_or_late;
    }

    public void setEarly_or_late(boolean early_or_late) {
        this.early_or_late = early_or_late;
    }

    public LeaveOffRequestDT0() {
    }

//    public RequestType getRequestType() {
//        return requestType;
//    }

    public SubRequestType getSubRequestType() {
        return subRequestType;
    }

    public LocalDate getDate() {
        return date;
    }



    public int getTimeOff() {
        return timeOff;
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



    public void setTimeOff(int timeOff) {
        this.timeOff = timeOff;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }
}
