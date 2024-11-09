package com.dsi301.mealmasterserver.dto.recipes;

import com.dsi301.mealmasterserver.dto.accounts.GeneralAccountDTO;
import com.dsi301.mealmasterserver.dto.instructions.GeneralInstructionDTO;
import com.dsi301.mealmasterserver.dto.recipeIngredients.GeneralRecipeIngredientDTO;
import com.dsi301.mealmasterserver.entities.Recipe;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class GeneralRecipeDTO {
    private UUID id;
    private String title;
    private String cover_img_url;
    private Integer cooking_time;
    private Integer serving_size;
    private GeneralAccountDTO owner;

    private List<GeneralInstructionDTO> instructions;

    private List<GeneralRecipeIngredientDTO> recipeIngredients;

    public GeneralRecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.cover_img_url = recipe.getCoverImgUrl();
        this.cooking_time = recipe.getCookingTime();
        this.serving_size = recipe.getServingSize();
        this.owner = new GeneralAccountDTO(recipe.getOwner());

        this.instructions = recipe.getInstructions().stream()
                .map(GeneralInstructionDTO::new)
                .collect(Collectors.toList());

        this.recipeIngredients = recipe.getRecipeIngredients().stream()
                .map(GeneralRecipeIngredientDTO::new)
                .collect(Collectors.toList());
    }

    public static Collection<GeneralRecipeDTO> fromEntities(Collection<Recipe> recipes) {
        return recipes.stream()
                .map(GeneralRecipeDTO::new)
                .toList();
    }
}
