package com.dsi301.mealmasterserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "instructions")
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
    private Integer step_number;
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer time_estimate;

    @ManyToOne
    private Recipe recipe;

    @Override
    public String toString() {
        return "Instruction{" +
                "id=" + id +
                ", step_number=" + step_number +
                ", text='" + text + '\'' +
                ", time_estimate=" + time_estimate +
                '}';
    }
}
