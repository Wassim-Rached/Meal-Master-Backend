package com.dsi301.mealmasterserver.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "measurement_units")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementUnit {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "measurementUnit")
    private List<RecipeIngredient> recipeIngredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeasurementUnit that)) return false;

        return this.name.equals(that.name);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "MeasurementUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
