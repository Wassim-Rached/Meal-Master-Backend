package com.dsi301.mealmasterserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {

        @Id
        @GeneratedValue
        private UUID id;

        @Column(nullable = false,unique = true)
        private String name;

        @ManyToMany(mappedBy = "tags")
        private List<Recipe> recipes;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Tag tag)) return false;
            return this.name.equals(tag.getName());
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }

        @Override
        public String toString() {
            return "#" + this.name;
        }
}
