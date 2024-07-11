package com.example.jwtspringsecurity.services.managerService;

import com.example.jwtspringsecurity.enities.RequestLeave;
import com.example.jwtspringsecurity.enities.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
public interface RequestService {
    Page<RequestLeave> getAllRequest(int page, int size);
    RequestLeave approveRequest(Long requestId);
    RequestLeave rejectRequest(Long requestId);
    Page<RequestLeave> searchRequest(String username,String email, int page, int size);
    Page<RequestLeave> searchRequestByType(RequestType requestType,int page,int size);
}
