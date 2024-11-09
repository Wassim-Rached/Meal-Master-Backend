package com.dsi301.mealmasterserver.dto.folders;

import com.dsi301.mealmasterserver.entities.Folder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class GeneralFolderDTO {
    private UUID id;
    private String name;

    public GeneralFolderDTO(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
    }

    public static Collection<GeneralFolderDTO> fromEntities(Collection<Folder> folders) {
        return folders.stream().map(GeneralFolderDTO::new).toList();
    }
}
