package com.dsi301.mealmasterserver.dto.recipeIngredients;

import com.dsi301.mealmasterserver.entities.Ingredient;
import com.dsi301.mealmasterserver.entities.MeasurementUnit;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.entities.RecipeIngredient;
import com.dsi301.mealmasterserver.interfaces.dto.ToEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class CreateRecipeIngredientRequestDTO implements ToEntity<RecipeIngredient, Recipe> {
    private double amount;
    private UUID ingredientId;
    private UUID measurementUnitId;

    @Override
    public RecipeIngredient toEntity(Recipe recipe) {
        return RecipeIngredient.builder()
                .amount(amount)
                .recipe(recipe)
                .ingredient(Ingredient.builder().id(ingredientId).build())
                .measurementUnit(MeasurementUnit.builder().id(measurementUnitId).build())
                .build();
    }

    public static CreateRecipeIngredientRequestDTO fake() {
        CreateRecipeIngredientRequestDTO dto = new CreateRecipeIngredientRequestDTO();
        dto.setAmount(1.0);
        dto.setIngredientId(UUID.randomUUID());
        dto.setMeasurementUnitId(UUID.randomUUID());
        return dto;
    }
}
