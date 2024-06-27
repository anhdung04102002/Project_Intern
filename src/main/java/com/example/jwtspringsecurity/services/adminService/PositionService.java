package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.Position;


import java.util.List;

public interface PositionService {
    List<Position> getAll();
    void savePosition(Position Position);
    Position getPosition(int id);
    void deletePosition(int id);
}
