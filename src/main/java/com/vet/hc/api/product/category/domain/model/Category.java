package com.vet.hc.api.product.category.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a category.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;

    private String name;
}
