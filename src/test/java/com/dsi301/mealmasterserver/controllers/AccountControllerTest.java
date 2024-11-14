package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.CreateAccountRequestDTO;
import com.dsi301.mealmasterserver.dto.accounts.UpdateAccountRequestDTO;
import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.repositories.AccountRepository;
import com.dsi301.mealmasterserver.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    // Creates a mock account with
    // a given username and password
    private Account createMockAccount(String username, String password) {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password)); // Encrypts the password
        return accountRepository.save(account); // Saves the mock account to the database
    }

    // Generates a JWT for a specific account,
    // used to simulate authorization
    private String generateJwtForAccount(Account account) {
        return jwtService.generateToken(account);
    }

    // Mocks the SecurityContext to simulate
    // an authenticated request for a specific account
    private void mockSecurityContext(Account account) {
        SecurityContext securityContext = org.mockito.Mockito.mock(SecurityContext.class);
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(account);
        SecurityContextHolder.setContext(securityContext);
    }

    // Clears the database before each test
    // to ensure an isolated test environment
    @BeforeEach
    void resetDatabase() {
        // Resets the repository to ensure
        // a clean state before each test
        accountRepository.deleteAll();
    }

    @Test
    void createAccount_Returns201Created() throws Exception {
        // Preparing a request DTO with new account data
        CreateAccountRequestDTO requestDTO = new CreateAccountRequestDTO();
        requestDTO.setUsername("newuser");
        requestDTO.setPassword("newpassword");

        // Performing the POST request to create a new account
        ResultActions result = mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)));

        // Verifying that the account creation was successful with a 201 status
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("Account Created Successfully"));
    }

    @Test
    void getMyAccount_ReturnsAccountInfo() throws Exception {
        // Creating a mock account and generating a JWT for authorization
        Account account = createMockAccount("user", "password");
        String jwt = generateJwtForAccount(account);
        // Sets the security context for the mock account
        mockSecurityContext(account);

        // Performing a GET request to retrieve account information
        ResultActions result = mockMvc.perform(get("/api/accounts/my")
                .header("Authorization", "Bearer " + jwt));

        // Checking that the correct account information is returned
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void updateMyAccount_Returns200Ok() throws Exception {
        // Creating a mock account, generating a JWT, and setting the security context
        Account account = createMockAccount("user", "password");
        String jwt = generateJwtForAccount(account);
        mockSecurityContext(account);

        // Preparing a DTO with new data for the update
        UpdateAccountRequestDTO requestDTO = new UpdateAccountRequestDTO();
        requestDTO.setAvatarUrl("http://new-avatar-url.com");

        // Performing a PUT request to update the account information
        ResultActions result = mockMvc.perform(put("/api/accounts/my")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)));

        // Asserting that the update was successful
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Account Updated Successfully"));
    }

    @Test
    void changePassword_Returns200Ok() throws Exception {
        // Creating a mock account and setting the security context
        Account account = createMockAccount("user", "password");
        String jwt = generateJwtForAccount(account);
        mockSecurityContext(account);

        // Performing a PUT request to change the account password
        ResultActions result = mockMvc.perform(put("/api/accounts/my/password")
                .header("Authorization", "Bearer " + jwt)
                .param("oldPassword", "password") // Providing the current password
                .param("newPassword", "newpassword")); // Providing the new password

        // Checking that the password change was successful
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Password Changed Successfully"));
    }

    @Test
    void deleteMyAccount_Returns200Ok() throws Exception {
        // Setting up a mock account with a security context and JWT
        Account account = createMockAccount("user", "password");
        String jwt = generateJwtForAccount(account);
        mockSecurityContext(account);

        // Sending a DELETE request to delete the account
        ResultActions result = mockMvc.perform(delete("/api/accounts/my")
                .header("Authorization", "Bearer " + jwt)
                .param("password", "password")); // Providing the account's password for verification

        // Verifying the deletion was successful
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Account Deleted Successfully"));
    }
}
