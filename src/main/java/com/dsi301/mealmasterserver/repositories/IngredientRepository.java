package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    Optional<Ingredient> findByName(String name);
}