package com.dsi301.mealmasterserver.specifications;

import com.dsi301.mealmasterserver.entities.Recipe;
import org.springframework.data.jpa.domain.Specification;

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

}
