package com.dsi301.mealmasterserver.runners;

import com.dsi301.mealmasterserver.entities.Ingredient;
import com.dsi301.mealmasterserver.entities.MeasurementUnit;
import com.dsi301.mealmasterserver.repositories.IngredientRepository;
import com.dsi301.mealmasterserver.repositories.MeasurementUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MeasurementUnitRepository measurementUnitRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public void run(String... args) throws Exception {
        // Insert Measurement Units
        for (MeasurementUnit unit : MeasurementUnit.init()) {
            measurementUnitRepository.findByName(unit.getName())
                    .orElseGet(() -> measurementUnitRepository.save(unit));
        }

        // Insert Ingredients
        for (Ingredient ingredient : Ingredient.init()) {
            ingredientRepository.findByName(ingredient.getName())
                    .orElseGet(() -> ingredientRepository.save(ingredient));
        }
    }
}

