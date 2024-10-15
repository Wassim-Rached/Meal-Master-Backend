package com.dsi301.mealmasterserver.specifications;

import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.entities.Tag;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

public class RecipeSpecification {

    public static Specification<Recipe> hasSearchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm == null) {
                return criteriaBuilder.conjunction();
            }
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
            );
        };
    }

    public static Specification<Recipe> hasCookingTime(Integer cookingTime) {
        return (root, query, criteriaBuilder) ->
                cookingTime == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("cooking_time"), cookingTime);
    }

    public static Specification<Recipe> hasServingSize(Integer servingSize) {
        return (root, query, criteriaBuilder) ->
                servingSize == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("serving_size"), servingSize);
    }

    public static Specification<Recipe> hasAllTags(List<Tag> tags) {
        return (root, query, criteriaBuilder) -> {
            if (tags == null || tags.isEmpty()) {
                return criteriaBuilder.conjunction(); // No tags provided, return all recipes
            }

            // Create a join with the tags
            Join<Recipe, Tag> tagJoin = root.join("tags"); // Adjust if your relationship is named differently

            // Create a predicate for each tag to ensure all are included
            Predicate[] predicates = tags.stream()
                    .map(tag -> criteriaBuilder.equal(tagJoin.get("id"), tag.getId()))
                    .toArray(Predicate[]::new);

            // Return true only if all tag predicates match
            return criteriaBuilder.and(predicates);
        };
    }

}
