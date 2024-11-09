package com.dsi301.mealmasterserver.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Favorite {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Recipe recipe;
}
