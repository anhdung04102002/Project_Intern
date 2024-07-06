package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminClientService {
    List<Client> getAllClient();
    Page<Client> getAllClient(int page, int size);
    Page<Client> getSearchClient(String keyword, int page, int size);
}
