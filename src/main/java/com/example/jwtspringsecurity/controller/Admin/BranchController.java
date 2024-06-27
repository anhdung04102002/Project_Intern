package com.example.jwtspringsecurity.controller.Admin;

import com.example.jwtspringsecurity.enities.Branch;
import com.example.jwtspringsecurity.enities.Position;
import com.example.jwtspringsecurity.services.adminService.BranchService;
import com.example.jwtspringsecurity.services.adminService.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class BranchController {
    @Autowired
    private BranchService branchService;

    @GetMapping("/branch_getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getAllBranches() {
        List<Branch> branches = branchService.getAll();
        List<Map<String, Object>> branchMaps = new ArrayList<>();

        // Convert Branch entities to Map<String, Object>
        for (Branch branch : branches) {
            Map<String, Object> branchMap = new HashMap<>();
            branchMap.put("name", branch.getName());
            branchMaps.add(branchMap);
        }

        return new ResponseEntity<>(branchMaps, HttpStatus.OK);
    }
}
