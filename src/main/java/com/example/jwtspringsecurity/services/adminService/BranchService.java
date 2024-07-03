package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.Branch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BranchService {
    List<Branch> getAll();
    void savebranch(Branch branch);
    Branch getbranch(int id);
    void deletebranch(int id);
    Page<Branch> getBranchWithPage(String name,int page, int size);
    Page<Branch> getAllBranches(int page, int size);
}
