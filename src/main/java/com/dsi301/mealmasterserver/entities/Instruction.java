package com.dsi301.mealmasterserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "instructions", uniqueConstraints = {@UniqueConstraint(columnNames = {"recipe_id", "step_number"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instruction {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Integer stepNumber;
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer timeEstimate;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Override
    public String toString() {
        return "Instruction{" +
                "id=" + id +
                ", step_number=" + stepNumber +
                ", text='" + text + '\'' +
                ", time_estimate=" + timeEstimate +
                '}';
    }
}
