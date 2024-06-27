package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.Position;
import com.example.jwtspringsecurity.repositories.PositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PositionServiceImpl implements PositionService{
    @Autowired
    private PositionRepo positionRepo;
    @Override
    public List<Position> getAll() {
        return this.positionRepo.findAll();
    }

    @Override
    public void savePosition(Position position) {
                this.positionRepo.save(position);
    }

    @Override
    public Position getPosition(int id) {
        return null;
    }

    @Override
    public void deletePosition(int id) {

    }
}
