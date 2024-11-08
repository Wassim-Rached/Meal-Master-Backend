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

    public static Specification<Recipe> hasCookingTimeRange(Integer minCookingTime, Integer maxCookingTime) {
        return (root, query, criteriaBuilder) -> {
            if (minCookingTime == null && maxCookingTime == null) {
                return criteriaBuilder.conjunction(); // No filter if both are null
            } else if (minCookingTime != null && maxCookingTime != null) {
                return criteriaBuilder.between(root.get("cookingTime"), minCookingTime, maxCookingTime);
            } else if (minCookingTime != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("cookingTime"), minCookingTime);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("cookingTime"), maxCookingTime);
            }
        };
    }

    public static Specification<Recipe> hasServingSizeRange(Integer minServingSize, Integer maxServingSize) {
        return (root, query, criteriaBuilder) -> {
            if (minServingSize == null && maxServingSize == null) {
                return criteriaBuilder.conjunction(); // No filter if both are null
            } else if (minServingSize != null && maxServingSize != null) {
                return criteriaBuilder.between(root.get("serving_size"), minServingSize, maxServingSize);
            } else if (minServingSize != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("serving_size"), minServingSize);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("serving_size"), maxServingSize);
            }
        };
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
