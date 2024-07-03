package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.Branch;
import com.example.jwtspringsecurity.repositories.BranchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Branch> getBranchWithPage(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // page - 1 vì PageRequest đánh số trang từ 0
        if (name != null) {
            return branchRepo.findByDisplayName(name, pageable);
        } else {
            return branchRepo.findAll(pageable);
        }

    }

    @Override
    public Page<Branch> getAllBranches(int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        return branchRepo.findAll(pageable);
    }
}
