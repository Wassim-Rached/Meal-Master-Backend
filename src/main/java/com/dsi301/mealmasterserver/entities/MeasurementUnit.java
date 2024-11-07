package com.dsi301.mealmasterserver.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Iterator;
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

    public MeasurementUnit(String name) {
        this.name = name;
    }

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

    public static Collection<MeasurementUnit> init() {
        return List.of(
                new MeasurementUnit("Cups"),
                new MeasurementUnit("Tablespoons"),
                new MeasurementUnit("Teaspoons"),
                new MeasurementUnit("Grams"),
                new MeasurementUnit("Milliliters"),
                new MeasurementUnit("Ounces"),
                new MeasurementUnit("Pounds"),
                new MeasurementUnit("Kilograms"),
                new MeasurementUnit("Liters"),
                new MeasurementUnit("Gallons"),
                new MeasurementUnit("Quarts"),
                new MeasurementUnit("Pints"),
                new MeasurementUnit("Inches"),
                new MeasurementUnit("Centimeters"),
                new MeasurementUnit("Pieces"),
                new MeasurementUnit("Whole"),
                new MeasurementUnit("Slices"),
                new MeasurementUnit("Leaves"),
                new MeasurementUnit("Cloves")
        );
    }
}
