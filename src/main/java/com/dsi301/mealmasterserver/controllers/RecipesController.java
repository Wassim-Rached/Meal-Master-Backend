package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.recipes.CreateRecipeRequestDTO;
import com.dsi301.mealmasterserver.dto.recipes.GeneralRecipeDTO;
import com.dsi301.mealmasterserver.entities.Ingredient;
import com.dsi301.mealmasterserver.entities.MeasurementUnit;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.entities.Tag;
import com.dsi301.mealmasterserver.repositories.IngredientRepository;
import com.dsi301.mealmasterserver.repositories.MeasurementUnitRepository;
import com.dsi301.mealmasterserver.repositories.RecipeRepository;
import com.dsi301.mealmasterserver.repositories.TagRepository;
import com.dsi301.mealmasterserver.specifications.RecipeSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private final TagRepository tagRepository;

    // recommendation
    @GetMapping("/recommendation")
    public Iterable<GeneralRecipeDTO> getRecommendation() {
        Iterable<Recipe> recipes = recipeRepository.findAll().subList(0, 3);
        return GeneralRecipeDTO.fromEntities(recipes);
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
            @RequestParam(required = false, defaultValue = "") String tags,
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
        System.out.println(sortBy);
        System.out.println(sortOrder);

        Sort sortObj = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sortObj);
        List<String> tagNames = List.of(tags.split(","));
        List<Tag> tagList = tagRepository.findAllByNameIn(tagNames);

        Specification<Recipe> spec = Specification.where(RecipeSpecification.hasSearchTerm(search))
                .and(RecipeSpecification.hasCookingTimeRange(minCookingTime, maxCookingTime))
                .and(RecipeSpecification.hasServingSizeRange(minServingSize, maxServingSize))
                .and(RecipeSpecification.hasAllTags(tagList));

        Page<Recipe> resultPage = recipeRepository.findAll(spec, pageable);
        return resultPage.map(GeneralRecipeDTO::new);
    }

    // create
    @PostMapping
    public UUID createRecipe(@RequestBody CreateRecipeRequestDTO requestBody) {
        Recipe recipe = requestBody.toEntity(tagRepository);
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
    public GeneralRecipeDTO getRecipeById(@PathVariable UUID id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new GeneralRecipeDTO(recipe);
    }

    // update by id

    // clear recipes
    @DeleteMapping
    public void clearRecipes() {
        recipeRepository.deleteAll();
    }
}