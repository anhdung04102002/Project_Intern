package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.LeaveType;
import com.example.jwtspringsecurity.repositories.LeaveTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeSerivceImpl implements LeaveTypeService{

  @Autowired
  private LeaveTypeRepo leaveTypeRepo;
    @Override
    public LeaveType save(LeaveType leaveType) {
        return leaveTypeRepo.save(leaveType);
    }
}
