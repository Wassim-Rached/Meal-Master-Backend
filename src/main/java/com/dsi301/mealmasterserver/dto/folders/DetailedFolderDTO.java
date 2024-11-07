package com.dsi301.mealmasterserver.dto.folders;

import com.dsi301.mealmasterserver.dto.recipes.GeneralRecipeDTO;
import com.dsi301.mealmasterserver.entities.Folder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class DetailedFolderDTO {
    private UUID id;
    private String name;
    private Collection<GeneralRecipeDTO> recipes;

    public DetailedFolderDTO(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
        this.recipes = GeneralRecipeDTO.fromEntities(folder.getRecipes());
    }
}
