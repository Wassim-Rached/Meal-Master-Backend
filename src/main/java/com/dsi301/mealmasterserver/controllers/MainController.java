package com.dsi301.mealmasterserver.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/api/health")
    public String health() {
        return "Server is up and running!";
    }
}
