package com.dsi301.mealmasterserver.dto.recipes;

import com.dsi301.mealmasterserver.dto.accounts.GeneralAccountDTO;
import com.dsi301.mealmasterserver.dto.instructions.GeneralInstructionDTO;
import com.dsi301.mealmasterserver.dto.recipeIngredients.GeneralRecipeIngredientDTO;
import com.dsi301.mealmasterserver.entities.Recipe;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@Setter
public class DetailedRecipeDTO {
    private UUID id;
    private String title;
    private String description;
    private String coverImgUrl;
    private Integer cookingTime;
    private Integer servingSize;
    private GeneralAccountDTO owner;

    private List<GeneralInstructionDTO> instructions;

    private List<GeneralRecipeIngredientDTO> recipeIngredients;

    public DetailedRecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.description = recipe.getDescription();
        this.coverImgUrl = recipe.getCoverImgUrl();
        this.cookingTime = recipe.getCookingTime();
        this.servingSize = recipe.getServingSize();
        this.owner = new GeneralAccountDTO(recipe.getOwner());


        this.instructions = recipe.getInstructions().stream()
                .map(GeneralInstructionDTO::new)
                .collect(Collectors.toList());

        this.recipeIngredients = recipe.getRecipeIngredients().stream()
                .map(GeneralRecipeIngredientDTO::new)
                .collect(Collectors.toList());
    }

    public static Iterable<DetailedRecipeDTO> fromEntities(Iterable<Recipe> recipes) {
        return StreamSupport.stream(recipes.spliterator(), false)
                .map(DetailedRecipeDTO::new)
                .toList();
    }
}
