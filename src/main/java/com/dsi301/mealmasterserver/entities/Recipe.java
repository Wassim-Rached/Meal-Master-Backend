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

    @Column(nullable = false, name = "cooking_time")
    private Integer cookingTime;

    @Column(nullable = false, name = "serving_size")
    private Integer servingSize;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Instruction> instructions;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "recipe_tags",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cover_img_url='" + cover_img_url + '\'' +
                ", cooking_time=" + cookingTime +
                ", serving_size=" + servingSize +
                ", instructions=" + instructions +
                ", recipeIngredients=" + recipeIngredients +
                '}';
    }

}
