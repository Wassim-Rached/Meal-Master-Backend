package com.dsi301.mealmasterserver.dto.recipes;

import com.dsi301.mealmasterserver.dto.instructions.CreateInstructionRequestDTO;
import com.dsi301.mealmasterserver.dto.instructions.GeneralInstructionDTO;
import com.dsi301.mealmasterserver.dto.recipeIngredients.CreateRecipeIngredientRequestDTO;
import com.dsi301.mealmasterserver.dto.recipeIngredients.GeneralRecipeIngredientDTO;
import com.dsi301.mealmasterserver.entities.Recipe;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@Setter
public class GeneralRecipeDTO {
    private UUID id;
    private String title;
    private String description;
    private String cover_img_url;
    private Integer cooking_time;
    private Integer serving_size;

    private List<GeneralInstructionDTO> instructions;

    private List<GeneralRecipeIngredientDTO> recipeIngredients;

    public GeneralRecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.description = recipe.getDescription();
        this.cover_img_url = recipe.getCover_img_url();
        this.cooking_time = recipe.getCooking_time();
        this.serving_size = recipe.getServing_size();

        System.out.println(recipe.getInstructions());
        System.out.println(this.instructions);
        this.instructions = recipe.getInstructions().stream()
                .map(GeneralInstructionDTO::new)
                .collect(Collectors.toList());
        System.out.println(this.instructions);

        this.recipeIngredients = recipe.getRecipeIngredients().stream()
                .map(GeneralRecipeIngredientDTO::new)
                .collect(Collectors.toList());
    }

    public static Iterable<GeneralRecipeDTO> fromEntities(Iterable<Recipe> recipes) {
        return StreamSupport.stream(recipes.spliterator(), false)
                .map(GeneralRecipeDTO::new)
                .toList();
    }
}
