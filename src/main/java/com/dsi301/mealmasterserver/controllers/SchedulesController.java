package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.schedules.CreateScheduleRequestDTO;
import com.dsi301.mealmasterserver.dto.schedules.DetailedScheduleDTO;
import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.entities.Folder;
import com.dsi301.mealmasterserver.entities.Schedule;
import com.dsi301.mealmasterserver.repositories.FolderRepository;
import com.dsi301.mealmasterserver.repositories.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class SchedulesController {

    private final ScheduleRepository scheduleRepository;
    private final FolderRepository folderRepository;

    // create schedule
    @PostMapping
    public ResponseEntity<String> createSchedule(@RequestBody CreateScheduleRequestDTO requestBody) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Schedule schedule = requestBody.toEntity(null);

        // get the folder by id
        Folder folder = folderRepository.findById(schedule.getFolder().getId()).orElseThrow(() -> new EntityNotFoundException("Folder not found with id: " + schedule.getFolder().getId()));
        schedule.setFolder(folder);

        schedule.setAccount(account);

        scheduleRepository.save(schedule);

        return ResponseEntity.ok("Schedule created successfully");
    }

    // delete schedule
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable UUID id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + id));

        if (!schedule.getAccount().getId().equals(account.getId())) {
            return ResponseEntity.badRequest().body("You are not authorized to delete this schedule");
        }

        scheduleRepository.delete(schedule);

        return ResponseEntity.ok("Schedule deleted successfully");
    }

    // get my schedules
    @GetMapping
    public ResponseEntity<Iterable<DetailedScheduleDTO>> getMySchedules() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Collection<Schedule> schedules = scheduleRepository.findAllByAccount(account);

        return ResponseEntity.ok(DetailedScheduleDTO.fromEntities(schedules));
    }

}
