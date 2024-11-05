package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.entities.Folder;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.repositories.FolderRepository;
import com.dsi301.mealmasterserver.repositories.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderRepository folderRepository;
    private final RecipeRepository recipeRepository;

    // get all folders
    @GetMapping
    public Iterable<Folder> getFolders(@RequestParam UUID accountId) {
        return folderRepository.findAllByAccountId(accountId);
    }

    // add recipe to folder
    @PostMapping
    public ResponseEntity<?> addRecipeToFolder(@RequestParam UUID folderId, @RequestParam UUID recipeId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new EntityNotFoundException("Folder not found"));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        folder.getRecipes().add(recipe);
        folderRepository.save(folder);

        return ResponseEntity.ok("Recipe added to folder successfully");
    }

    // remove recipe from folder
    @DeleteMapping
    public ResponseEntity<?> removeRecipeFromFolder(@RequestParam UUID folderId, @RequestParam UUID recipeId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new EntityNotFoundException("Folder not found"));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        folder.getRecipes().remove(recipe);
        folderRepository.save(folder);

        return ResponseEntity.ok("Recipe removed from folder successfully");
    }
}
