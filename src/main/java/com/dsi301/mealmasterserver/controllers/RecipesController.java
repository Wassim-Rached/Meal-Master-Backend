package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.recipes.CreateRecipeRequestDTO;
import com.dsi301.mealmasterserver.dto.recipes.DetailedRecipeDTO;
import com.dsi301.mealmasterserver.entities.Ingredient;
import com.dsi301.mealmasterserver.entities.MeasurementUnit;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.repositories.IngredientRepository;
import com.dsi301.mealmasterserver.repositories.MeasurementUnitRepository;
import com.dsi301.mealmasterserver.repositories.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipeRepository recipeRepository;
    private final MeasurementUnitRepository measurementUnitRepository;
    private final IngredientRepository ingredientRepository;

    // recommendation
    @GetMapping("/recommendation")
    public Iterable<DetailedRecipeDTO> getRecommendation() {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        return DetailedRecipeDTO.fromEntities(recipes);
    }

    // search
    @GetMapping("/search")
    public Iterable<DetailedRecipeDTO> searchRecipes(@RequestParam(defaultValue = "") String title) {
        Iterable<Recipe> recipes = recipeRepository.findAllByTitleContaining(title);
        return DetailedRecipeDTO.fromEntities(recipes);
    }

    // create
    @PostMapping
    public UUID createRecipe(@RequestBody CreateRecipeRequestDTO requestBody) {
        Recipe recipe = requestBody.toEntity(null);
        // get its ingredients and measurement units by name
        for (var recipeIngredient : recipe.getRecipeIngredients()){
            Ingredient ingredient = ingredientRepository.findById(recipeIngredient.getIngredient().getId()).orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + recipeIngredient.getIngredient().getId()));
            MeasurementUnit measurementUnit = measurementUnitRepository.findById(recipeIngredient.getMeasurementUnit().getId()).orElseThrow(() -> new EntityNotFoundException("Measurement unit not found with id: " + recipeIngredient.getMeasurementUnit().getId()));

            // update recipe ingredient with the new ingredient and measurement unit
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setMeasurementUnit(measurementUnit);
        }
        return recipeRepository.save(recipe).getId();
    }

    // get by id
    @GetMapping("/{id}")
    public DetailedRecipeDTO getRecipeById(@PathVariable UUID id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new DetailedRecipeDTO(recipe);
    }

    // update by id

    // clear recipes
    @DeleteMapping
    public void clearRecipes() {
        recipeRepository.deleteAll();
    }
}