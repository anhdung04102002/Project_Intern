package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.Branch;

import java.util.List;

public interface BranchService {
    List<Branch> getAll();
    void savebranch(Branch branch);
    Branch getbranch(int id);
    void deletebranch(int id);
}
