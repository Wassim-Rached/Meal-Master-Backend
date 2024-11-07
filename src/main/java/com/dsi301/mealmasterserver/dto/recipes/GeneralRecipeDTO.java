package com.dsi301.mealmasterserver.dto.recipes;

import com.dsi301.mealmasterserver.entities.Recipe;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class GeneralRecipeDTO {
    private UUID id;
    private String title;
    private String cover_img_url;
    private Integer cooking_time;
    private Integer serving_size;

    public GeneralRecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.cover_img_url = recipe.getCover_img_url();
        this.cooking_time = recipe.getCooking_time();
        this.serving_size = recipe.getServing_size();
    }

    public static Collection<GeneralRecipeDTO> fromEntities(Collection<Recipe> recipes) {
        return recipes.stream()
                .map(GeneralRecipeDTO::new)
                .toList();
    }
}
