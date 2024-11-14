package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.CreateAccountRequestDTO;
import com.dsi301.mealmasterserver.dto.accounts.GeneralAccountDTO;
import com.dsi301.mealmasterserver.dto.accounts.UpdateAccountRequestDTO;
import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.exceptions.InputValidationException;
import com.dsi301.mealmasterserver.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    // create
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequestDTO createAccountRequestDTO) {
        boolean accountAlreadyExists =  accountRepository.findByUsername(createAccountRequestDTO.getUsername()).isPresent();

        if (accountAlreadyExists) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }

        Account account = createAccountRequestDTO.toEntity(null);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return new ResponseEntity<>("Account Created Successfully", HttpStatus.CREATED);
    }

    // my account
    @GetMapping("/my")
    public ResponseEntity<GeneralAccountDTO> getMyAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(new GeneralAccountDTO(account), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/my")
    public ResponseEntity<?> deleteMyAccount(@RequestParam String password) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!passwordEncoder.matches(password, account.getPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        accountRepository.delete(account);
        return new ResponseEntity<>("Account Deleted Successfully", HttpStatus.OK);
    }

    // update
    @PutMapping("/my")
    public ResponseEntity<?> updateMyAccount(@RequestBody UpdateAccountRequestDTO requestDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        account.setAvatarUrl(requestDTO.getAvatarUrl());

        accountRepository.save(account);
        return new ResponseEntity<>("Account Updated Successfully", HttpStatus.OK);
    }

    // change password
    @PutMapping("/my/password")
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
        return new ResponseEntity<>("Password Changed Successfully", HttpStatus.OK);
    }



}
