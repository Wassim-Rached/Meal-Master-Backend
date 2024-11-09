package com.dsi301.mealmasterserver.dto.recipes;

import com.dsi301.mealmasterserver.dto.instructions.CreateInstructionRequestDTO;
import com.dsi301.mealmasterserver.dto.recipeIngredients.CreateRecipeIngredientRequestDTO;
import com.dsi301.mealmasterserver.entities.Instruction;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.entities.RecipeIngredient;
import com.dsi301.mealmasterserver.exceptions.InputValidationException;
import com.dsi301.mealmasterserver.interfaces.dto.ToEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateRecipeRequestDTO implements ToEntity<Recipe, Void> {
    private String title;
    private String description;
    private String cover_img_url;
    private Integer cooking_time;
    private Integer serving_size;
    private List<String> tags;

    private List<CreateInstructionRequestDTO> instructions;

    private List<CreateRecipeIngredientRequestDTO> recipeIngredients;

    @Override
    public Recipe toEntity(Void aVoid) {
        if (title == null || title.isBlank())
            throw new InputValidationException("Title is required");
        if (cooking_time == null)
            throw new InputValidationException("Cooking time is required");
        if (serving_size == null)
            throw new InputValidationException("Serving size is required");
        if (instructions == null)
            throw new InputValidationException("Instructions are required");
        if (recipeIngredients == null)
            throw new InputValidationException("Recipe ingredients are required");

        Recipe recipe = Recipe.builder()
                .title(title)
                .description(description)
                .coverImgUrl(cover_img_url)
                .cookingTime(cooking_time)
                .servingSize(serving_size)
                .build();

        List<Instruction> instructions = this.instructions.stream()
                .map(instruction -> instruction.toEntity(recipe))
                .toList();

        List<RecipeIngredient> recipeIngredients = this.recipeIngredients.stream()
                .map(recipeIngredient -> recipeIngredient.toEntity(recipe))
                .toList();

        recipe.setInstructions(instructions);
        recipe.setRecipeIngredients(recipeIngredients);

        return recipe;
    }
}
