package com.dsi301.mealmasterserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "recipe_ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredient {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private double amount;

    @ManyToOne
    private Recipe recipe;

    @ManyToOne(cascade = CascadeType.ALL)
    private Ingredient ingredient;

    @ManyToOne(cascade = CascadeType.ALL)
    private MeasurementUnit measurementUnit;

    @Override
public String toString() {
        return "RecipeIngredient{" +
                "id=" + id +
                ", amount=" + amount +
                ", ingredient=" + ingredient +
                ", measurementUnit=" + measurementUnit +
                '}';
    }
}
