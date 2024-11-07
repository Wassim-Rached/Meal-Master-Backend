package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.Favorite;
import com.dsi301.mealmasterserver.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    // find all recipes for certain account
    @Query("SELECT f.recipe FROM Favorite f WHERE f.account.id = :accountId")
    Iterable<Recipe> findAllByAccountId(UUID accountId);

    Optional<Favorite> findByAccountIdAndRecipeId(UUID accountId, UUID recipeId);

    boolean existsByAccountIdAndRecipeId(UUID id, UUID recipeId);
}
