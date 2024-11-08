package com.dsi301.mealmasterserver.runners;

import com.dsi301.mealmasterserver.entities.*;
import com.dsi301.mealmasterserver.repositories.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MeasurementUnitRepository measurementUnitRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Insert Measurement Units
        List<String> measurementUnitNames = readJson("measurement_units.json", new TypeReference<>() {});
        for (String name : measurementUnitNames) {
            MeasurementUnit unit = new MeasurementUnit(name);
            measurementUnitRepository.findByName(unit.getName())
                    .orElseGet(() -> measurementUnitRepository.save(unit));
        }

        // Insert Ingredients
        List<String> ingredientNames = readJson("ingredients.json", new TypeReference<>() {});
        for (String name : ingredientNames) {
            Ingredient ingredient = new Ingredient(name);
            ingredientRepository.findByName(ingredient.getName())
                    .orElseGet(() -> ingredientRepository.save(ingredient));
        }

        // Load existing ingredients
        Map<String, Ingredient> ingredientMap = ingredientRepository.findAll().stream()
                .collect(Collectors.toMap(Ingredient::getName, ingredient -> ingredient));
        // load existing measurement units
        Map<String, MeasurementUnit> measurementUnitMap = measurementUnitRepository.findAll().stream()
                .collect(Collectors.toMap(MeasurementUnit::getName, unit -> unit));


        // Insert Recipes
        List<Recipe> recipes = readJson("recipes.json", new TypeReference<>() {});
        for (Recipe recipe : recipes) {
            Optional<Recipe> recipeExist = recipeRepository.findByTitle(recipe.getTitle());

            if (recipeExist.isPresent()) {
                continue;
            }

            for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
                recipeIngredient.setRecipe(recipe);

                //
                Ingredient ingredient = ingredientMap.get(recipeIngredient.getIngredient().getName());
                if (ingredient != null) {
                    recipeIngredient.setIngredient(ingredient);
                }

                //
                MeasurementUnit measurementUnit = measurementUnitMap.get(recipeIngredient.getMeasurementUnit().getName());
                if (measurementUnit != null) {
                    recipeIngredient.setMeasurementUnit(measurementUnit);
                }
            }

            for (Instruction instruction : recipe.getInstructions()) {
                instruction.setRecipe(recipe);
            }

            recipeRepository.save(recipe);
        }
    }

    private <T> T readJson(String fileName, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(new ClassPathResource(fileName).getInputStream(), typeReference);
    }
}