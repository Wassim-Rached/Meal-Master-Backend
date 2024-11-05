package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
