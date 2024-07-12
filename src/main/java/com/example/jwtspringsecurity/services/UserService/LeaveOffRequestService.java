package com.example.jwtspringsecurity.services.UserService;

import com.example.jwtspringsecurity.dto.LeaveOffRequestDT0;
import com.example.jwtspringsecurity.dto.LeaveRemoteOrOnsiteRequestDTO;
import com.example.jwtspringsecurity.enities.RequestLeave;
import com.example.jwtspringsecurity.enities.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeaveOffRequestService {
    RequestLeave saveLeaveOffRequest(LeaveOffRequestDT0 leaveOffRequestDT0);
    RequestLeave saveLeaveOnsiteRequest(LeaveRemoteOrOnsiteRequestDTO leaveRemoteOrOnsiteRequestDTO);
    RequestLeave saveLeaveRemoteRequest(LeaveRemoteOrOnsiteRequestDTO leaveRemoteOrOnsiteRequestDTO);
    Page<RequestLeave> searchRequestByUserAndMonthAndRequestType(int year, int month, RequestType requestType, Long userId, int page,int size);
}
