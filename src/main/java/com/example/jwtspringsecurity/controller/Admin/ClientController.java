package com.example.jwtspringsecurity.controller.Admin;

import com.example.jwtspringsecurity.enities.Branch;
import com.example.jwtspringsecurity.enities.Client;
import com.example.jwtspringsecurity.services.adminService.AdminClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/admin")
public class ClientController {
    @Autowired
    private AdminClientService adminClientService;
    @GetMapping("/client_getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Client>> getAllClients(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "5") int size) {
        Page<Client> clients = adminClientService.getAllClient(page, size);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    @GetMapping("/client_search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Client>> searchClients(@RequestParam(required = false) String keyword,
                                                      @RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "5") int size) {
        Page<Client> clients = adminClientService.getSearchClient(keyword, page, size);
        return new ResponseEntity<>(clients, HttpStatus.OK);
     }
}

