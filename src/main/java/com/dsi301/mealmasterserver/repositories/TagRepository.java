package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByName(String name);

    @Query(value = "SELECT * FROM tags t WHERE t.name ILIKE %:name% LIMIT 5", nativeQuery = true)
    List<Tag> findAllByNameContaining(@Param("name") String name);

    @Query(value = "SELECT * FROM tags t WHERE t.name IN :split", nativeQuery = true)
    List<Tag> findAllByNameIn(@Param("split") List<String> split);
}
