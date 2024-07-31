package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.LeaveOffRequestDT0;
import com.example.jwtspringsecurity.dto.LeaveRemoteOrOnsiteRequestDTO;
import com.example.jwtspringsecurity.enities.RequestLeave;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    LeaveOffRequestDT0 requestLeaveToLeaveOffRequestDT0(RequestLeave requestLeave);
    @Mappings({
            @Mapping(source = "subRequestType", target = "subRequestType"),
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "timeOff", target = "timeOff"),
            @Mapping(source = "reason", target = "reason"),
            @Mapping(source = "leaveTypeId", target = "leaveType.id"),
            @Mapping(source = "early_or_late", target = "early_or_late")
    })
    RequestLeave leaveOffRequestDT0ToRequestLeaves(LeaveOffRequestDT0 leaveOffRequestDT0);

    LeaveRemoteOrOnsiteRequestDTO requestLeaveToLeaveRemoteOrOnsiteRequestDTO(RequestLeave requestLeave);
    @Mappings({
            @Mapping(source = "subRequestType", target = "subRequestType"),
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "reason", target = "reason")
    })
    RequestLeave leaveRemoteOrOnsiteRequestDTOToRequestLeaves(LeaveRemoteOrOnsiteRequestDTO leaveRemoteOrOnsiteRequestDTO);

}
