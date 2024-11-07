package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.folders.CreateFolderDTO;
import com.dsi301.mealmasterserver.dto.folders.DetailedFolderDTO;
import com.dsi301.mealmasterserver.dto.folders.GeneralFolderDTO;
import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.entities.Folder;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.repositories.FolderRepository;
import com.dsi301.mealmasterserver.repositories.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Iterable<GeneralFolderDTO> getFolders() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return GeneralFolderDTO.fromEntities(folderRepository.findAllByAccountId(account.getId()));
    }

    // get details of a folder
    @GetMapping("/{folderId}")
    public ResponseEntity<DetailedFolderDTO> getFolder(@PathVariable UUID folderId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new EntityNotFoundException("Folder not found"));
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!account.getId().equals(folder.getAccount().getId())) {
            return ResponseEntity.status(403).body(null);
        }

        return ResponseEntity.ok(new DetailedFolderDTO(folder));
    }

    // create folder
    @PostMapping
    public ResponseEntity<UUID> createFolder(@RequestBody CreateFolderDTO createFolderDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Folder folder = createFolderDTO.toEntity(account);
        folderRepository.save(folder);

        return ResponseEntity.ok(folder.getId());
    }

    // delete folder
    @DeleteMapping("/{folderId}")
    public ResponseEntity<?> deleteFolder(@PathVariable UUID folderId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new EntityNotFoundException("Folder not found"));

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!account.getId().equals(folder.getAccount().getId())) {
            return ResponseEntity.status(403).body("You are not allowed to delete this folder");
        }

        folderRepository.delete(folder);
        return ResponseEntity.ok("Folder deleted successfully");
    }

    // add recipe to folder
    @PostMapping("/{folderId}/recipes")
    public ResponseEntity<?> addRecipesToFolder(@PathVariable UUID folderId, @RequestParam UUID recipeId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new EntityNotFoundException("Folder not found"));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!account.getId().equals(folder.getAccount().getId())) {
            return ResponseEntity.status(403).body("You are not allowed to add recipe to this folder");
        }

        folder.getRecipes().add(recipe);
        folderRepository.save(folder);

        return ResponseEntity.ok("Recipe added to folder successfully");
    }

    // remove recipe from folder
    @DeleteMapping("{folderId}/recipes")
    public ResponseEntity<?> removeRecipeFromFolder(@PathVariable UUID folderId, @RequestParam UUID recipeId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new EntityNotFoundException("Folder not found"));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!account.getId().equals(folder.getAccount().getId())) {
            return ResponseEntity.status(403).body("You are not allowed to remove recipe from this folder");
        }

        folder.getRecipes().remove(recipe);
        folderRepository.save(folder);

        return ResponseEntity.ok("Recipe removed from folder successfully");
    }
}
