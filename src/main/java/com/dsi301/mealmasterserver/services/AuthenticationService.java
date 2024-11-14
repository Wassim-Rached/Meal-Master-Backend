package com.dsi301.mealmasterserver.services;

import com.dsi301.mealmasterserver.dto.LoginRequestDTO;
import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public Account authenticate(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }
}
