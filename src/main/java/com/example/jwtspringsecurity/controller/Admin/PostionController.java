package com.example.jwtspringsecurity.controller.Admin;

import com.example.jwtspringsecurity.enities.Position;
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
@RequestMapping("/api/admin/")
public class PostionController {
    @Autowired
    private PositionService postionService;

    @GetMapping("/position_getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String,Object>>> getAllPositions() {

        List<Position> positions = postionService.getAll();
        List<Map<String,Object>> positionMap = new ArrayList<>();;
        for(Position position:positions ){
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("name",position.getName());
            positionMap.add(objectMap);
        }
        return new ResponseEntity<>(positionMap, HttpStatus.OK);
    }
}
