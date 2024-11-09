package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.CreateAccountRequestDTO;
import com.dsi301.mealmasterserver.dto.accounts.GeneralAccountDTO;
import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.exceptions.InputValidationException;
import com.dsi301.mealmasterserver.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    // create
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequestDTO createAccountRequestDTO) {
        accountRepository.findByUsername(createAccountRequestDTO.getUsername())
                .ifPresent(account -> {
                    throw new InputValidationException("Username already exists");
                });
        Account account = createAccountRequestDTO.toEntity(null);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return new ResponseEntity<>("Account Created Successfuly", HttpStatus.CREATED);
    }

    // my account
    @GetMapping("/my")
    public ResponseEntity<GeneralAccountDTO> getMyAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(new GeneralAccountDTO(account), HttpStatus.OK);
    }

}
