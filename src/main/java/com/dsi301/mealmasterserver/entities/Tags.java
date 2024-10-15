package com.dsi301.mealmasterserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tags {

        @Id
        @GeneratedValue
        private UUID id;

        @Column(nullable = false,unique = true)
        private String name;

        @OneToMany(mappedBy = "tag")
        private List<RecipeTag> recipeTags;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Tags tag)) return false;
            return this.name.equals(tag.getName());
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }

        @Override
        public String toString() {
            return "tags{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
}
