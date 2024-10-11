package com.dsi301.mealmasterserver.dto.recipeIngredients;

import com.dsi301.mealmasterserver.dto.ingredients.GeneralIngredientDTO;
import com.dsi301.mealmasterserver.dto.measurementUnit.GeneralMeasurementUnitDTO;
import com.dsi301.mealmasterserver.entities.MeasurementUnit;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.entities.RecipeIngredient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Getter
@Setter
public class GeneralRecipeIngredientDTO {
    private UUID id;
    private Double amount;

    private GeneralIngredientDTO ingredient;
    private GeneralMeasurementUnitDTO measurementUnit;

    public GeneralRecipeIngredientDTO(RecipeIngredient recipeIngredient) {
        this.id = recipeIngredient.getId();
        this.amount = recipeIngredient.getAmount();
        this.ingredient = new GeneralIngredientDTO(recipeIngredient.getIngredient());
        this.measurementUnit = new GeneralMeasurementUnitDTO(recipeIngredient.getMeasurementUnit());
    }
}
