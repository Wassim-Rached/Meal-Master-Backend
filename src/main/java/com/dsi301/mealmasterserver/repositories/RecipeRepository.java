package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    @Query("SELECT r FROM Recipe r WHERE r.title ILIKE %:title%")
    Iterable<Recipe> findAllByTitleContaining(String title);

    Optional<Recipe> findByTitle(String title);
}
