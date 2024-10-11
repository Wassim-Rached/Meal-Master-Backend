package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.ingredients.GeneralIngredientDTO;
import com.dsi301.mealmasterserver.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientsController {

    private final IngredientRepository ingredientRepository;

    @GetMapping
    public Iterable<GeneralIngredientDTO> getIngredients() {
        return GeneralIngredientDTO.fromEntities(ingredientRepository.findAll());
    }
}
