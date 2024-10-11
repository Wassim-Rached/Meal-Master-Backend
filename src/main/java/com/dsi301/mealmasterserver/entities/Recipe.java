package com.dsi301.mealmasterserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String cover_img_url;

    @Column(nullable = false)
    private Integer cooking_time;

    @Column(nullable = false)
    private Integer serving_size;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Instruction> instructions;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients;


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cover_img_url='" + cover_img_url + '\'' +
                ", cooking_time=" + cooking_time +
                ", serving_size=" + serving_size +
                ", instructions=" + instructions +
                ", recipeIngredients=" + recipeIngredients +
                '}';
    }

}
