package com.example.jwtspringsecurity.controller.Admin;

import com.example.jwtspringsecurity.enities.LeaveType;
import com.example.jwtspringsecurity.services.adminService.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")

public class LeaveTypeController {
    @Autowired
    private LeaveTypeService leaveTypeService;
    @PostMapping("/leaveType_add")
    public ResponseEntity<?> addLeaveType(@RequestBody LeaveType leaveType){
        LeaveType savedLeaveType = leaveTypeService.save(leaveType);
        return ResponseEntity.ok(savedLeaveType);

    }
}
