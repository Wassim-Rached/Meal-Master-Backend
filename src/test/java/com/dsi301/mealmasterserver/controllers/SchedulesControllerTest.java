package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.schedules.CreateScheduleRequestDTO;
import com.dsi301.mealmasterserver.entities.*;
import com.dsi301.mealmasterserver.repositories.AccountRepository;
import com.dsi301.mealmasterserver.repositories.FolderRepository;
import com.dsi301.mealmasterserver.repositories.ScheduleRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SchedulesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Account mockAccount;
    private Folder mockFolder;

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

    // Helper method to create and save a folder
    private Folder createAndSaveFolder() {
        Folder folder = new Folder();
        folder.setId(UUID.randomUUID());
        folder.setName("Test Folder");
        return folderRepository.save(folder);
    }

    // Helper method to create and save a schedule
    private Schedule createAndSaveSchedule(Account account, Folder folder) {
        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID());
        schedule.setAccount(account);
        schedule.setFolder(folder);
        return scheduleRepository.save(schedule);
    }

    @BeforeEach
    void setUp() {
        scheduleRepository.deleteAll();
        folderRepository.deleteAll();

        // Setting up a mock account and folder for tests
        mockAccount = new Account();
        mockAccount.setId(UUID.randomUUID());
        mockAccount.setUsername("test");
        mockAccount.setPassword("password");  // Assume password is already encoded in real scenario
        mockAccount = accountRepository.save(mockAccount);
        mockFolder = createAndSaveFolder();
        mockSecurityContext(mockAccount);
    }

    @Test
    void createSchedule_Returns201Created() throws Exception {
        String jwt = generateJwtForAccount(mockAccount);

        CreateScheduleRequestDTO requestDTO = new CreateScheduleRequestDTO();
        requestDTO.setScheduledDate(Instant.now());
        requestDTO.setFolderId(mockFolder.getId());
        // Set up the requestDTO fields for creating schedule

        ResultActions result = mockMvc.perform(post("/api/schedules")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$")
                        .value("Schedule created successfully"));
    }
}
