package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {
    Collection<Folder> findAllByAccountId(UUID accountId);
}
