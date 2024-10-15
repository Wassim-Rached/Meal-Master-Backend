package com.dsi301.mealmasterserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "recipe_tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeTag {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    private Tags tag;

    @Override
    public String toString() {
        return "RecipeTag{" +
                "id=" + id +
                ", recipe=" + recipe +
                ", tag=" + tag +
                '}';
    }
}
