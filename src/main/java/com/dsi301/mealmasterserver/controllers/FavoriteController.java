package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.recipes.GeneralRecipeDTO;
import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.entities.Favorite;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.repositories.AccountRepository;
import com.dsi301.mealmasterserver.repositories.FavoriteRepository;
import com.dsi301.mealmasterserver.repositories.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final AccountRepository accountRepository;
    private final RecipeRepository recipeRepository;

    // add recipe to favorites
    @PostMapping
    public void addFavorite(@RequestParam UUID accountId, @RequestParam UUID recipeId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account not found"));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        Optional<Favorite> favorite = favoriteRepository.findByAccountIdAndRecipeId(accountId, recipeId);

        if (favorite.isPresent()) {
            return;
        }

        Favorite a = new Favorite();
        a.setAccount(account);
        a.setRecipe(recipe);

        favoriteRepository.save(a);
    }

    // remove recipe from favorites
    @DeleteMapping
    public void removeFavorite(@RequestParam UUID accountId, @RequestParam UUID recipeId) {
        Optional<Favorite> favorite = favoriteRepository.findByAccountIdAndRecipeId(accountId, recipeId);

        if (favorite.isEmpty()) {
            return;
        }

        favoriteRepository.delete(favorite.get());
    }


    // get all favorite recipes
    @GetMapping
    public Iterable<GeneralRecipeDTO> getFavorites(@RequestParam UUID accountId) {
        var recipes = favoriteRepository.findAllByAccountId(accountId);
        return GeneralRecipeDTO.fromEntities(recipes);
    }


}
