package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, UUID> {
}
