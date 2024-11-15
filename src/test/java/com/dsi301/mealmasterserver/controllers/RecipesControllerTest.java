package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.recipes.CreateRecipeRequestDTO;
import com.dsi301.mealmasterserver.entities.*;
import com.dsi301.mealmasterserver.repositories.*;
import com.dsi301.mealmasterserver.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecipesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Account mockAccount;

    // Helper method to generate JWT for an account
    private String generateJwtForAccount(Account account) {
        return jwtService.generateToken(account);
    }

    // Helper method to mock the security context for the current account
    private void mockSecurityContext(Account account) {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(account);
        SecurityContextHolder.setContext(securityContext);
    }

    // Generate a CreateRecipeRequestDTO object with the required fields
    private CreateRecipeRequestDTO generateFakeCreateRecipeRequestDTO() {
        return CreateRecipeRequestDTO.fake();
    }

    @BeforeEach
    void setUp() {
        recipeRepository.deleteAll();
        accountRepository.deleteAll();

        // Setting up a mock account for tests
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setUsername("test");
        account.setPassword(passwordEncoder.encode("password")); // Encrypts the password
        mockAccount = accountRepository.save(account);
        mockSecurityContext(mockAccount);
    }

    @Test
    void createRecipe_Returns201Created() throws Exception {
        String jwt = generateJwtForAccount(mockAccount);

        CreateRecipeRequestDTO requestDTO = generateFakeCreateRecipeRequestDTO();

        ResultActions result = mockMvc.perform(post("/api/recipes")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)));

        // uuid
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isString());
    }
}
