package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {
    Iterable<Folder> findAllByAccountId(UUID accountId);
}
