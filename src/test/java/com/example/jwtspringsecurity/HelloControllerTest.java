package com.example.jwtspringsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAdminAccess() throws Exception {
        // Replace "adminToken" with a valid JWT token for an admin user
        String adminToken = "Bearer adminToken";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/hello")
                .header("Authorization", adminToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}