package com.dsi301.mealmasterserver.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredient> recipeIngredients;

    public Ingredient( String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ingredient ingredient)) return false;
        return this.name.equals(ingredient.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static Collection<Ingredient> init() {
        return List.of(
                new Ingredient("Flour"),
                new Ingredient("Sugar"),
                new Ingredient("Eggs"),
                new Ingredient("Milk"),
                new Ingredient("Butter"),
                new Ingredient("Salt"),
                new Ingredient("Pepper"),
                new Ingredient("Olive oil"),
                new Ingredient("Vegetable oil"),
                new Ingredient("Baking powder"),
                new Ingredient("Yeast"),
                new Ingredient("Vanilla extract"),
                new Ingredient("Cocoa powder"),
                new Ingredient("Honey"),
                new Ingredient("Brown sugar"),
                new Ingredient("Baking soda"),
                new Ingredient("Cinnamon"),
                new Ingredient("Nutmeg"),
                new Ingredient("Lemon juice"),
                new Ingredient("Garlic"),
                new Ingredient("Onion"),
                new Ingredient("Tomato paste"),
                new Ingredient("Soy sauce")
        );
    }
}
