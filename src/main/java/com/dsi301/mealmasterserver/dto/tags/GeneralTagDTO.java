package com.dsi301.mealmasterserver.dto.tags;

import com.dsi301.mealmasterserver.dto.ingredients.GeneralIngredientDTO;
import com.dsi301.mealmasterserver.entities.Ingredient;
import com.dsi301.mealmasterserver.entities.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.stream.StreamSupport;

@Getter
@Setter
public class GeneralTagDTO {
    private UUID id;
    private String name;

    public GeneralTagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }

    public static Iterable<GeneralTagDTO> fromEntities(Iterable<Tag> all) {
        return StreamSupport.stream(all.spliterator(), false)
                .map(GeneralTagDTO::new)
                .toList();
    }
}
