package com.dsi301.mealmasterserver.dto.recipes;

import com.dsi301.mealmasterserver.dto.instructions.CreateInstructionRequestDTO;
import com.dsi301.mealmasterserver.dto.recipeIngredients.CreateRecipeIngredientRequestDTO;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.interfaces.dto.ToEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UpdateRecipeRequestDTO implements ToEntity<Recipe,Recipe> {
    private String title;
    private String description;
    private String coverImgUrl;
    private Integer cookingTime;
    private Integer servingSize;

    private List<CreateInstructionRequestDTO> instructions = new ArrayList<>();

    private List<CreateRecipeIngredientRequestDTO> recipeIngredients = new ArrayList<>();

    @Override
    public Recipe toEntity(Recipe recipe) {
        if (title != null && !title.isBlank())
            recipe.setTitle(title);
        
        if (cookingTime != null)
            recipe.setCookingTime(cookingTime);
        
        if (servingSize != null)
            recipe.setServingSize(servingSize);
        
        if (description != null)
            recipe.setDescription(description);
        
        if (coverImgUrl != null)
            recipe.setCoverImgUrl(coverImgUrl);
        
        if (instructions != null)
            recipe.setInstructions(instructions.stream()
                    .map(instruction -> instruction.toEntity(recipe))
                    .toList());
        
        if (recipeIngredients != null)
            recipe.setRecipeIngredients(recipeIngredients.stream()
                    .map(recipeIngredient -> recipeIngredient.toEntity(recipe))
                    .toList());

        return recipe;
    }
}
