package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.entities.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {
    @Query("SELECT r FROM Recipe r WHERE r.title ILIKE %:title%")
    Iterable<Recipe> findAllByTitleContaining(String title);

    Optional<Recipe> findByTitle(String title);

    Page<Recipe> findByOwner(Account account, Pageable pageable);
}
