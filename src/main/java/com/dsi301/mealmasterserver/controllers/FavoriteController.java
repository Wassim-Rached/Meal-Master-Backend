package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.recipes.DetailedRecipeDTO;
import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.entities.Favorite;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.repositories.FavoriteRepository;
import com.dsi301.mealmasterserver.repositories.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final RecipeRepository recipeRepository;

    // add recipe to favorites
    @PostMapping
    public void addFavorite(@RequestParam UUID recipeId) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        Optional<Favorite> favorite = favoriteRepository.findByAccountIdAndRecipeId(account.getId(), recipeId);

        if (favorite.isPresent()) {
            return;
        }

        Favorite a = new Favorite();
        a.setAccount(account);
        a.setRecipe(recipe);

        favoriteRepository.save(a);
    }

    // remove recipe from favorites
    @DeleteMapping("{recipeId}")
    public void removeFavorite(@PathVariable UUID recipeId) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Favorite> favorite = favoriteRepository.findByAccountIdAndRecipeId(account.getId(), recipeId);


        if (favorite.isEmpty()) {
            return;
        }

        favoriteRepository.delete(favorite.get());
    }

    // get all favorite recipes
    @GetMapping
    public Iterable<DetailedRecipeDTO> getFavorites() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var recipes = favoriteRepository.findAllByAccountId(account.getId());
        return DetailedRecipeDTO.fromEntities(recipes);
    }

}
