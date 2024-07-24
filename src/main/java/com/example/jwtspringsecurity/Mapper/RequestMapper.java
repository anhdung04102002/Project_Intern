package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.LeaveOffRequestDT0;
import com.example.jwtspringsecurity.dto.LeaveRemoteOrOnsiteRequestDTO;
import com.example.jwtspringsecurity.enities.RequestLeave;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    LeaveOffRequestDT0 requestLeaveToLeaveOffRequestDT0(RequestLeave requestLeave);
    RequestLeave leaveOffRequestDT0ToRequestLeaves(LeaveOffRequestDT0 leaveOffRequestDT0);

    LeaveRemoteOrOnsiteRequestDTO requestLeaveToLeaveRemoteOrOnsiteRequestDTO(RequestLeave requestLeave);
    RequestLeave leaveRemoteOrOnsiteRequestDTOToRequestLeave(LeaveRemoteOrOnsiteRequestDTO leaveRemoteOrOnsiteRequestDTO);

}
