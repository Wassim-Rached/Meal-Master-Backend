package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    Collection<Schedule> findAllByAccount(Account account);
}
