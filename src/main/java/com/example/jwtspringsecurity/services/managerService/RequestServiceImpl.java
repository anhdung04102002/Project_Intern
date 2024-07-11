package com.example.jwtspringsecurity.services.managerService;

import com.example.jwtspringsecurity.enities.RequestLeave;
import com.example.jwtspringsecurity.enities.RequestType;
import com.example.jwtspringsecurity.repositories.RequestLeaveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RequestServiceImpl implements RequestService{

    @Autowired
    private RequestLeaveRepository requestLeaveRepository;

    @Override
    public Page<RequestLeave> getAllRequest(int page,int size) {
        return requestLeaveRepository.findByStatus("PENDING", PageRequest.of(page - 1, size));
    }
    @Override
    public RequestLeave approveRequest(Long requestId) {
        RequestLeave request = requestLeaveRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found with id: " + requestId));
        request.setStatus("APPROVED");
        return requestLeaveRepository.save(request);
    }

    @Override
    public RequestLeave rejectRequest(Long requestId) {
        RequestLeave request = requestLeaveRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found with id: " + requestId));
        request.setStatus("REJECT");
        return requestLeaveRepository.save(request);
    }

    @Override
    public Page<RequestLeave> searchRequest(String username, String email, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if ((username == null || username.isEmpty()) && (email == null || email.isEmpty())) {
            // If both username and email are not provided, return all requests
            return requestLeaveRepository.findAll(pageable);
        } else {
            // Use the existing method for filtering by username and email
            return requestLeaveRepository.findByUsernameAndEmail(
                    username == null ? "" : username,
                    email == null ? "" : email,
                    pageable);
        }
    }

    @Override
    public Page<RequestLeave> searchRequestByType(RequestType requestType, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (requestType.equals(RequestType.ALL)) {
            // If requestType is ALL or null, return all requests
            return requestLeaveRepository.findAll(pageable);
        } else {
            // Search by specific RequestType
            return requestLeaveRepository.findByRequestType(requestType, pageable);
        }
    }

}
