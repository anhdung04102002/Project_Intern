package com.example.jwtspringsecurity.controller.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/github")
public class GithubController {
    @GetMapping("/login")
    public String githubLogin() {
        // Redirect to GitHub login
        return "redirect:/oauth2/authorization/github";
    }
}
