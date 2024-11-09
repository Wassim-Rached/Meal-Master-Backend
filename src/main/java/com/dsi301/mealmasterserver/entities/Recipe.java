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

    private String coverImgUrl;

    @Column(nullable = false, name = "cooking_time")
    private Integer cookingTime;

    @Column(nullable = false, name = "serving_size")
    private Integer servingSize;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Instruction> instructions;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_account_id")
    private Account owner;


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cover_img_url='" + coverImgUrl + '\'' +
                ", cooking_time=" + cookingTime +
                ", serving_size=" + servingSize +
                ", instructions=" + instructions +
                ", recipeIngredients=" + recipeIngredients +
                '}';
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe recipe)) return false;

        return id != null ? id.equals(recipe.id) : super.equals(o);
    }


}
