package com.dsi301.mealmasterserver.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "account_id"})})
public class Folder {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "folder_recipes",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Set<Recipe> recipes = Set.of();
}
