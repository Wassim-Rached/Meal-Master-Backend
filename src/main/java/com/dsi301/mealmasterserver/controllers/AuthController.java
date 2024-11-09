package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.LoginRequestDTO;
import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.services.AuthenticationService;
import com.dsi301.mealmasterserver.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDTO request) {
        Account authenticatedAccount;
        try{
            authenticatedAccount = authenticationService.authenticate(request);
        }catch (Exception e){
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String jwtToken = jwtService.generateToken(authenticatedAccount);

        return ResponseEntity.ok(jwtToken);
    }
}
