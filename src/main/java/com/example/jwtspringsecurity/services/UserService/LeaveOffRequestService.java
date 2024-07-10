package com.example.jwtspringsecurity.services.UserService;

import com.example.jwtspringsecurity.dto.LeaveOffRequestDT0;
import com.example.jwtspringsecurity.dto.LeaveRemoteOrOnsiteRequestDTO;
import com.example.jwtspringsecurity.enities.LeaveType;
import com.example.jwtspringsecurity.enities.RequestLeave;
import com.example.jwtspringsecurity.Mapper.RequestMapper;
import com.example.jwtspringsecurity.enities.RequestType;
import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.repositories.LeaveTypeRepo;
import com.example.jwtspringsecurity.repositories.RequestLeaveRepository;
import com.example.jwtspringsecurity.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LeaveOffRequestService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private LeaveTypeRepo leaveTypeRepository;
    @Autowired
    private final RequestLeaveRepository requestLeaveRepository;
    private final RequestMapper requestMapper;

    @Autowired
    public LeaveOffRequestService(RequestLeaveRepository requestLeaveRepository, RequestMapper requestMapper) {
        this.requestLeaveRepository = requestLeaveRepository;
        this.requestMapper = requestMapper;
    }

    public RequestLeave saveLeaveOffRequest(LeaveOffRequestDT0 leaveOffRequestDT0) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        ;

        if (leaveOffRequestDT0.getDate() == null) {
            throw new IllegalArgumentException("The date field must not be null");
        }
        RequestLeave requestLeave = requestMapper.leaveOffRequestDT0ToRequestLeave(leaveOffRequestDT0);
        LeaveType leaveType = leaveTypeRepository.findById(leaveOffRequestDT0.getLeaveTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid leave type ID"));
        requestLeave.setLeaveType(leaveType);
        requestLeave.setUser(currentUser);
        requestLeave.setRequestType(RequestType.OFF);
        requestLeave.setStatus("PENDING");
        return requestLeaveRepository.save(requestLeave);
    }

    public RequestLeave saveLeaveOnsiteRequest(LeaveRemoteOrOnsiteRequestDTO leaveRemoteOrOnsiteRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        ;
        if (leaveRemoteOrOnsiteRequestDTO.getDate() == null) {
            throw new IllegalArgumentException("The date field must not be null");
        }
        RequestLeave requestLeave = requestMapper.leaveRemoteOrOnsiteRequestDTOToRequestLeave(leaveRemoteOrOnsiteRequestDTO);
        requestLeave.setUser(currentUser);
        requestLeave.setRequestType(RequestType.ONSITE);
        requestLeave.setStatus("PENDING");
        return requestLeaveRepository.save(requestLeave);
    }
    public RequestLeave saveLeaveRemoteRequest(LeaveRemoteOrOnsiteRequestDTO leaveRemoteOrOnsiteRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        ;
        if (leaveRemoteOrOnsiteRequestDTO.getDate() == null) {
            throw new IllegalArgumentException("The date field must not be null");
        }
        RequestLeave requestLeave = requestMapper.leaveRemoteOrOnsiteRequestDTOToRequestLeave(leaveRemoteOrOnsiteRequestDTO);
        requestLeave.setUser(currentUser);
        requestLeave.setRequestType(RequestType.REMOTE);
        requestLeave.setStatus("PENDING");
        return requestLeaveRepository.save(requestLeave);
    }
}