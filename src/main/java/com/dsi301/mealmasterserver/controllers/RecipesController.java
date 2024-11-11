package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.recipes.CreateRecipeRequestDTO;
import com.dsi301.mealmasterserver.dto.recipes.DetailedRecipeDTO;
import com.dsi301.mealmasterserver.dto.recipes.GeneralRecipeDTO;
import com.dsi301.mealmasterserver.dto.recipes.UpdateRecipeRequestDTO;
import com.dsi301.mealmasterserver.entities.*;
import com.dsi301.mealmasterserver.repositories.IngredientRepository;
import com.dsi301.mealmasterserver.repositories.MeasurementUnitRepository;
import com.dsi301.mealmasterserver.repositories.RecipeIngredientRepository;
import com.dsi301.mealmasterserver.repositories.RecipeRepository;
import com.dsi301.mealmasterserver.specifications.RecipeSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipeRepository recipeRepository;
    private final MeasurementUnitRepository measurementUnitRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    // create
    @PostMapping
    public UUID createRecipe(@RequestBody CreateRecipeRequestDTO requestBody) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Recipe recipe = requestBody.toEntity(null);
        recipe.setOwner(account);
        account.getRecipes().add(recipe);

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


    // recommendation
    @GetMapping("/recommendation")
    public Iterable<GeneralRecipeDTO> getRecommendation() {
        List<Recipe> recipes = recipeRepository.findAll();
        return GeneralRecipeDTO.fromEntities(recipes.subList(0, Math.min(recipes.size(), 3)));
    }

    // search
    @GetMapping("/search")
    public Page<GeneralRecipeDTO> searchRecipes(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer minCookingTime,
            @RequestParam(required = false) Integer maxCookingTime,
            @RequestParam(required = false) Integer minServingSize,
            @RequestParam(required = false) Integer maxServingSize,
            @RequestParam(required = false, defaultValue = "id,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String sortBy = "id";
        String sortOrder = "asc";

        if (sort != null) {
            String[] sortParts = sort.split(",");
            if (sortParts.length == 2) {
                sortBy = sortParts[0].trim();
                sortOrder = sortParts[1].trim();
            }
        }

        Sort sortObj = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sortObj);

        Specification<Recipe> spec = Specification.where(RecipeSpecification.hasSearchTerm(search))
                .and(RecipeSpecification.hasCookingTimeRange(minCookingTime, maxCookingTime))
                .and(RecipeSpecification.hasServingSizeRange(minServingSize, maxServingSize));

        Page<Recipe> resultPage = recipeRepository.findAll(spec, pageable);
        return resultPage.map(GeneralRecipeDTO::new);
    }


    // get my
    @GetMapping("/my")
    public Page<GeneralRecipeDTO> getMyRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> resultPage = recipeRepository.findByOwner(account, pageable);
        return resultPage.map(GeneralRecipeDTO::new);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable UUID id, @RequestBody UpdateRecipeRequestDTO requestBody) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));

        if (!recipe.getOwner().getId().equals(account.getId())) {
            return ResponseEntity.status(403).body("You are not allowed to update this recipe");
        }

        recipe = requestBody.toEntity(recipe);


        // get its ingredients and measurement units by name
        for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()){
            Ingredient ingredient = ingredientRepository.findById(recipeIngredient.getIngredient().getId()).orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + recipeIngredient.getIngredient().getId()));
            MeasurementUnit measurementUnit = measurementUnitRepository.findById(recipeIngredient.getMeasurementUnit().getId()).orElseThrow(() -> new EntityNotFoundException("Measurement unit not found with id: " + recipeIngredient.getMeasurementUnit().getId()));

            // update recipe ingredient with the new ingredient and measurement unit
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setMeasurementUnit(measurementUnit);
        }

        recipeIngredientRepository.saveAll(recipe.getRecipeIngredients());


        recipeRepository.save(recipe);
        return ResponseEntity.ok("Recipe updated successfully");
    }

    // delete
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteRecipe(@PathVariable UUID id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));

        if (!recipe.getOwner().getId().equals(account.getId())) {
            return ResponseEntity.status(403).body("You are not allowed to delete this recipe");
        }

        recipe.getOwner().getRecipes().remove(recipe);

        recipeRepository.delete(recipe);
        return ResponseEntity.ok("Recipe deleted successfully");
    }

    // get by id
    @GetMapping("/{id}")
    public DetailedRecipeDTO getRecipeById(@PathVariable UUID id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new DetailedRecipeDTO(recipe);
    }

    // update by id


}