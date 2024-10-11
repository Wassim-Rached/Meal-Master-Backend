package com.dsi301.mealmasterserver.dto.ingredients;

import com.dsi301.mealmasterserver.entities.Ingredient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Getter
@Setter
public class GeneralIngredientDTO {
    private UUID id;
    private String name;

    public GeneralIngredientDTO(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
    }

    public static Iterable<GeneralIngredientDTO> fromEntities(Iterable<Ingredient> all) {
        return StreamSupport.stream(all.spliterator(), false)
                .map(GeneralIngredientDTO::new)
                .toList();
    }
}
