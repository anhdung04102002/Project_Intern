package com.example.jwtspringsecurity.services.adminService;

import com.example.jwtspringsecurity.enities.Client;
import com.example.jwtspringsecurity.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminClientServiceImpl implements AdminClientService {
    @Autowired
    private ClientRepo clientRepo;
    @Override
    public List<Client> getAllClient() {
        return clientRepo.findAll();
    }

    @Override
    public Page<Client> getAllClient(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Client> clients = clientRepo.findAll(pageable);

        clients.forEach(client -> {
            String code = convertNameToCode(client.getName());
            client.setCode(code);
        });

        return clients;
    }

    @Override
    public Page<Client> getSearchClient(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (keyword != null) {
            return clientRepo.search(keyword, pageable);
        }
        else {
            return clientRepo.findAll(pageable);
        }
    }
    private String convertNameToCode(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }

        String[] words = name.split(" ");
        StringBuilder code = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                code.append(Character.toUpperCase(word.charAt(0)));
            }
        }

        return code.toString();
    }
}
