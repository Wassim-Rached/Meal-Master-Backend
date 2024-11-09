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

    public static List<String> initNames() {
        return List.of(
                "Flour",
                "Sugar",
                "Eggs",
                "Milk",
                "Butter",
                "Salt",
                "Pepper",
                "Olive oil",
                "Vegetable oil",
                "Baking powder",
                "Yeast",
                "Vanilla extract",
                "Cocoa powder",
                "Honey",
                "Brown sugar",
                "Baking soda",
                "Cinnamon",
                "Nutmeg",
                "Lemon juice",
                "Garlic",
                "Onion",
                "Tomato paste",
                "Soy sauce"
        );
    }

    public static Collection<Ingredient> init() {
        return initNames().stream().map(Ingredient::new).toList();
    }
}
