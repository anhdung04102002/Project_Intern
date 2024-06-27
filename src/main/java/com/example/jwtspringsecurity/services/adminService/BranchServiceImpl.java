package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.Branch;
import com.example.jwtspringsecurity.repositories.BranchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    private BranchRepo branchRepo;

    @Override
    public List<Branch> getAll() {
        return this.branchRepo.findAll();
    }

    @Override
    public void savebranch(Branch branch) {
            this.branchRepo.save(branch);
    }

    @Override
    public Branch getbranch(int id) {
        return null;
    }

    @Override
    public void deletebranch(int id) {

    }
}
